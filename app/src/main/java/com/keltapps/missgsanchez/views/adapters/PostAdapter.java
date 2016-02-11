package com.keltapps.missgsanchez.views.adapters;


import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.fragments.PostFragment;
import com.keltapps.missgsanchez.models.ViewHolderLoad;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.DepthPageTransformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class PostAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static final int TYPE_REGULAR = 0;
    private static final int TYPE_LOAD = 1;
    private Context context;
    private HashMap<Integer, Integer> mapState = new HashMap<>();
    private HashMap<Integer, Cursor> mapCursor = new HashMap<>();

    public PostAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        switch (getItemViewType(cursor.getPosition())) {
            case TYPE_REGULAR:
                ((ViewHolderPost) viewHolder).bindProfile(context, cursor, mapState);
                break;
            case TYPE_LOAD:
                ((ViewHolderLoad) viewHolder).bindProfile();
                break;
            default:
                ((ViewHolderPost) viewHolder).bindProfile(context, cursor, mapState);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case TYPE_REGULAR:
                return new ViewHolderPost(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.post_item_blog, viewGroup, false), mapCursor);
            case TYPE_LOAD:
                return new ViewHolderLoad(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_load_more, viewGroup, false));
            default:
                return new ViewHolderPost(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.post_item_blog, viewGroup, false), mapCursor);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getCursor() == null || PostFragment.noMorePages)
            return TYPE_REGULAR;
        return getCursor().getCount() - 1 == position ? TYPE_LOAD : TYPE_REGULAR;

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ViewHolderPost)
            mapState.put(viewHolder.getAdapterPosition(), ((ViewHolderPost) viewHolder).mPager.getCurrentItem());
    }

    public static class ViewHolderPost extends RecyclerView.ViewHolder implements LoaderManager.LoaderCallbacks<Cursor> {
        private static String TAG_ID_POST = "id_post";
        private static String TAG = ViewHolderPost.class.getSimpleName();
        Context context;
        TextView title;
        TextView time;
        TextView photo;
        Calendar calendar;
        public ViewPager mPager;
        private CursorPagerAdapter cursorPagerAdapter;
        HashMap<Integer, Cursor> mapCursor;


        public ViewHolderPost(Context context, View itemView, HashMap<Integer, Cursor> mapCursor) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.linear_title_textView);
            time = (TextView) itemView.findViewById(R.id.extraInformation_textView_time);
            photo = (TextView) itemView.findViewById(R.id.extraInformation_textView_photo);
            calendar = Calendar.getInstance();
            this.context = context;
            mPager = (ViewPager) itemView.findViewById(R.id.post_item_pager);
            this.mapCursor = mapCursor;
        }

        public void bindProfile(Context context, Cursor cursor, HashMap<Integer, Integer> mapState) {
            title.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.TITLE)));
            time.setText(getDifferenceTime(context, cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.DATE))));
            mPager.setPageTransformer(true, new DepthPageTransformer());
            Cursor cursorPhotos = mapCursor.get(getAdapterPosition());
            if(cursorPhotos != null)
                photo.setText("1 / " + cursorPhotos.getCount());
            cursorPagerAdapter = new CursorPagerAdapter(cursorPhotos);
            mPager.setAdapter(cursorPagerAdapter);
            Integer adapterPosition = mapState.get(getAdapterPosition());
            if (adapterPosition != null)
                mPager.setCurrentItem(adapterPosition);
            else {
                Bundle bundle = new Bundle();
                bundle.putInt(TAG_ID_POST, cursor.getInt(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.ID_POST)));
                ((AppCompatActivity) context).getLoaderManager().initLoader(getAdapterPosition(), bundle, this);
            }
            mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(final int position) {
                    String text = photo.getText().toString();
                    text = text.substring(1);
                    photo.setText((position+1) + text);
                }
            });
        }

        private String getDifferenceTime(Context context, String stringDate) {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String year, month, day, time;
            if (stringDate.contains("-")) {
                year = stringDate.substring(0, stringDate.indexOf("-"));
                stringDate = stringDate.substring(stringDate.indexOf("-") + 1);
                month = stringDate.substring(0, stringDate.indexOf("-"));
                stringDate = stringDate.substring(stringDate.indexOf("-") + 1);
                day = stringDate.substring(0, stringDate.indexOf("T"));
                time = stringDate.substring(stringDate.indexOf("T") + 1);
                stringDate = day + "-" + month + "-" + year + " " + time;
            }

            Date date;
            try {
                date = simpleDateFormat.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return " ";
            }


            //milliseconds
            long different = calendar.getTimeInMillis() - date.getTime();


            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
            Resources res = context.getResources();
            if (elapsedDays == 1)
                return elapsedDays + " " + res.getString(R.string.time_day) + " " + res.getString(R.string.time_ago);
            else if (elapsedDays != 0)
                return elapsedDays + " " + res.getString(R.string.time_days) + " " + res.getString(R.string.time_ago);
            else if (elapsedHours == 1)
                return elapsedHours + " " + res.getString(R.string.time_hour) + " " + res.getString(R.string.time_ago);
            else if (elapsedHours != 0)
                return elapsedHours + " " + res.getString(R.string.time_hours) + " " + res.getString(R.string.time_ago);
            else if (elapsedMinutes == 1)
                return elapsedMinutes + " " + res.getString(R.string.time_minute) + " " + res.getString(R.string.time_ago);
            else if (elapsedMinutes != 0)
                return elapsedMinutes + " " + res.getString(R.string.time_minutes) + " " + res.getString(R.string.time_ago);
            else if (elapsedSeconds == 1)
                return elapsedSeconds + " " + res.getString(R.string.time_second) + " " + res.getString(R.string.time_ago);
            else
                return elapsedSeconds + " " + res.getString(R.string.time_seconds) + " " + res.getString(R.string.time_ago);
        }


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(context, EntryProvider.CONTENT_URI_PHOTOS, null, ScriptDatabase.ColumnPhotosBlog.ID_POST + " =?", new String[]{Integer.toString(args.getInt(TAG_ID_POST))}, ScriptDatabase.ColumnPhotosBlog.ID + " ASC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mapCursor.put(loader.getId(), data);
            cursorPagerAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(android.content.Loader<Cursor> loader) {
            cursorPagerAdapter.swapCursor(null);
        }


        private class CursorPagerAdapter extends PagerAdapter {
            public Cursor cursor;
            private LayoutInflater inflater;

            public CursorPagerAdapter(Cursor cursor) {
                super();
                this.cursor = cursor;
                inflater = LayoutInflater.from(context);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LinearLayout layout = (LinearLayout) inflater.inflate(
                        R.layout.fragment_screen_slide_page, container, false);

                if (cursor.moveToPosition(position)) {
                    String urlMiniature, urlMiniatureFullResolution, urlMiniatureName;
                    urlMiniature = urlMiniatureFullResolution = urlMiniatureName = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnPhotosBlog.URL));

                    if (urlMiniature.contains("localhost"))
                        urlMiniature = urlMiniature.replace("localhost", "192.168.1.17");

                    if (urlMiniature.contains("/"))
                        urlMiniatureName = urlMiniature.substring(urlMiniature.lastIndexOf("/"));
                    if (urlMiniatureName.contains("-"))
                        urlMiniatureFullResolution = urlMiniature.substring(0, urlMiniature.lastIndexOf("/")) + urlMiniatureName.substring(0, urlMiniatureName.lastIndexOf("-")) + urlMiniature.substring(urlMiniature.lastIndexOf("."));


                    LoadImages.setImage(context, urlMiniatureFullResolution, urlMiniature, (ImageView) layout.findViewById(R.id.screen_slide_image_view));
                    container.addView(layout);
                    return layout;
                }
                return null;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                if (cursor != null) {
                    return cursor.getCount();
                }
                return 0;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view.equals(object);
            }

            public Cursor swapCursor(Cursor cursor) {
                Cursor oldCursor = cursor;
                this.cursor = cursor;
                notifyDataSetChanged();
                int count = cursor.getCount();
                if(count != 0)
                    photo.setText("1 / " + cursor.getCount());
                else
                    photo.setText("0 / " + cursor.getCount());
                return oldCursor;
            }

        }

    }
}
