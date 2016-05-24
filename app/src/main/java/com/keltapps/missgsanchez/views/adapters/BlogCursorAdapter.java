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
import com.keltapps.missgsanchez.fragments.BlogTabFragment;
import com.keltapps.missgsanchez.models.ViewHolderLoad;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.DepthPageTransformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class BlogCursorAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static final int TYPE_REGULAR = 0;
    private static final int TYPE_LOAD = 1;
    private final Context context;
    private final HashMap<Integer, Integer> mapState = new HashMap<>();
    private final HashMap<Integer, Cursor> mapCursor = new HashMap<>();

    public BlogCursorAdapter(Context context) {
        super(null);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        switch (getItemViewType(cursor.getPosition())) {
            case TYPE_REGULAR:
                ((ViewHolderPost) viewHolder).bindHolder(context, cursor, mapState);
                break;
            case TYPE_LOAD:
              //  ((ViewHolderLoad) viewHolder).bindProfile();
                break;
            default:
                ((ViewHolderPost) viewHolder).bindHolder(context, cursor, mapState);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_REGULAR:
                return new ViewHolderPost(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_blog, viewGroup, false), mapCursor);
            case TYPE_LOAD:
                return new ViewHolderLoad(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_load_more, viewGroup, false));
            default:
                return new ViewHolderPost(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_blog, viewGroup, false), mapCursor);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getCursor() == null || BlogTabFragment.noMorePages)
            return TYPE_REGULAR;
        return getCursor().getCount() - 1 == position ? TYPE_LOAD : TYPE_REGULAR;

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ViewHolderPost)
            mapState.put(viewHolder.getAdapterPosition(), ((ViewHolderPost) viewHolder).mPager.getCurrentItem());
    }

    public static class ViewHolderPost extends RecyclerView.ViewHolder implements LoaderManager.LoaderCallbacks<Cursor> {
        private static final String TAG_ID_POST = "id_post";
        private static String TAG = ViewHolderPost.class.getSimpleName();
        final Context context;
        final View container;
        final TextView title;
        final TextView time;
        final TextView photo;
        final Calendar calendar;
        public final ViewPager mPager;
        private CursorPagerAdapter cursorPagerAdapter;
        final  HashMap<Integer, Cursor> mapCursor;
        final View includeTitleBackground;
        final View includeExtraBackground;
        final View includeExtraTimeImage;
        final View includeExtraPhotoImage;
        int positionCursorPhotos = 0;


        public ViewHolderPost(Context context, View itemView, HashMap<Integer, Cursor> mapCursor) {
            super(itemView);
            container = itemView.findViewById(R.id.item_blog_cardView);
            title = (TextView) itemView.findViewById(R.id.linear_title_textView);
            time = (TextView) itemView.findViewById(R.id.extraInformation_textView_time);
            photo = (TextView) itemView.findViewById(R.id.extraInformation_textView_photo);
            calendar = Calendar.getInstance();
            this.context = context;
            mPager = (ViewPager) itemView.findViewById(R.id.post_item_pager);
            this.mapCursor = mapCursor;
            includeTitleBackground = itemView.findViewById(R.id.linear_title_container);
            includeExtraBackground = itemView.findViewById(R.id.extraInformation_container);
            includeExtraTimeImage = itemView.findViewById(R.id.extraInformation_imageView_time);
            includeExtraPhotoImage = itemView.findViewById(R.id.extraInformation_imageView_photo);
        }

        public void bindHolder(final Context context, final Cursor cursor, HashMap<Integer, Integer> mapState) {
            title.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnBlog.TITLE)));
            time.setText(getDifferenceTime(context, cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnBlog.DATE))));
            mPager.setPageTransformer(true, new DepthPageTransformer());
            final Cursor cursorPhotos = mapCursor.get(getAdapterPosition());
            if (cursorPhotos != null)
                photo.setText(context.getString(R.string.photosSlideCount, 1, cursorPhotos.getCount()));
            cursorPagerAdapter = new CursorPagerAdapter(cursor, cursorPhotos);
            mPager.setAdapter(cursorPagerAdapter);
            Integer adapterPosition = mapState.get(getAdapterPosition());
            if (adapterPosition != null)
                mPager.setCurrentItem(adapterPosition);
            else {
                Bundle bundle = new Bundle();
                bundle.putInt(TAG_ID_POST, cursor.getInt(cursor.getColumnIndex(ScriptDatabase.ColumnBlog.ID_POST)));
                ((AppCompatActivity) context).getLoaderManager().initLoader(getAdapterPosition(), bundle, this);
            }
            mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(final int position) {
                    String text = photo.getText().toString();
                    text = text.substring(text.lastIndexOf(" ")+1);
                    photo.setText(context.getString(R.string.photosSlideCount, position + 1, Integer.parseInt(text)));
                    positionCursorPhotos = position;
                }
            });
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeToInsideFragment(cursor, getAdapterPosition(), positionCursorPhotos);
                }
            });
        }

        private String getDifferenceTime(Context context, String stringDate) {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", java.util.Locale.getDefault());
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


        private void changeToInsideFragment(Cursor cursor, int position, int positionCursorPhotos) {
            List<String> listPhotos = new ArrayList<>();
            Cursor cursorPhotos = cursorPagerAdapter.getCursorPhotos();
            if(cursorPhotos == null)
                return;
            int cursorPhotosPosition = cursorPhotos.getPosition();
            if (cursorPhotos.moveToFirst()) {
                do {
                    listPhotos.add(cursorPhotos.getString(cursorPhotos.getColumnIndex(ScriptDatabase.ColumnPhotosBlog.URL)));
                } while (cursorPhotos.moveToNext());
            }
            cursorPagerAdapter.getCursorPhotos().moveToPosition(cursorPhotosPosition);
            int cursorPosition = cursor.getPosition();
            cursor.moveToPosition(position);
            ((BlogTabFragment.OnBlogTabListener) context).onClickPostListener(listPhotos, positionCursorPhotos, time.getText().toString(),
                    title.getText().toString(),
                    cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnBlog.TEXT)),
                    cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnBlog.URL)));
            cursor.moveToPosition(cursorPosition);
        }


        private class CursorPagerAdapter extends PagerAdapter {
            public final Cursor cursor;
            public Cursor cursorPhotos;
            private final LayoutInflater inflater;

            public CursorPagerAdapter(Cursor cursor, Cursor cursorPhotos) {
                super();
                this.cursor = cursor;
                this.cursorPhotos = cursorPhotos;
                inflater = LayoutInflater.from(context);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {

                LinearLayout layout = (LinearLayout) inflater.inflate(
                        R.layout.fragment_slide_screen_image_page, container, false);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeToInsideFragment(cursor, getAdapterPosition(), positionCursorPhotos);
                    }
                });

                if (cursorPhotos.moveToPosition(position)) {
                    String urlMiniature, urlMiniatureFullResolution, urlMiniatureName;
                    urlMiniature = urlMiniatureFullResolution = urlMiniatureName = cursorPhotos.getString(cursorPhotos.getColumnIndex(ScriptDatabase.ColumnPhotosBlog.URL));

                    if (urlMiniature.contains("localhost"))
                        urlMiniature = urlMiniature.replace("localhost", VolleySingleton.WORDPRESS);

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
                if (cursorPhotos != null) {
                    return cursorPhotos.getCount();
                }
                return 0;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view.equals(object);
            }

            public Cursor swapCursor(Cursor cursor) {
                this.cursorPhotos = cursor;
                notifyDataSetChanged();
                if (cursor == null)
                    return null;
                int count = cursor.getCount();
                if (count != 0)
                    photo.setText(context.getString(R.string.photosSlideCount,1,cursor.getCount()));
                else
                    photo.setText(context.getString(R.string.photosSlideCount,0,cursor.getCount()));
                return cursor;
            }

            public Cursor getCursorPhotos() {
                return cursorPhotos;
            }

        }

    }
}
