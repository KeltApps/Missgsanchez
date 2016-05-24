package com.keltapps.missgsanchez.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.fragments.YouTubeTabFragment;
import com.keltapps.missgsanchez.fragments.YouTubeTabFragment.OnYouTubeFullScreenListener;
import com.keltapps.missgsanchez.models.ViewHolderLoad;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.CircleTransform;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by sergio on 30/03/16 for KelpApps.
 */
public class YoutubeCursorAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static final int TYPE_REGULAR = 0;
    private static final int TYPE_LOAD = 1;
    private final Context context;
    private OnYouTubeFullScreenListener onYouTubeFullScreenListener;

    public YoutubeCursorAdapter(Context context, OnYouTubeFullScreenListener onYouTubeFullScreenListener) {
        super(null);
        this.context = context;
        this.onYouTubeFullScreenListener = onYouTubeFullScreenListener;
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        switch (getItemViewType(cursor.getPosition())) {
            case TYPE_REGULAR:
                ((ViewHolderYoutube) viewHolder).bindHolder(cursor, onYouTubeFullScreenListener);
                break;
            case TYPE_LOAD:
                break;
            default:
                ((ViewHolderYoutube) viewHolder).bindHolder(cursor, onYouTubeFullScreenListener);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_REGULAR:
                return new ViewHolderYoutube(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_youtube, viewGroup, false));
            case TYPE_LOAD:
                return new ViewHolderLoad(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_load_more, viewGroup, false));
            default:
                return new ViewHolderYoutube(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_youtube, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getCursor() == null || YouTubeTabFragment.noMorePages)
            return TYPE_REGULAR;
        return getCursor().getCount() - 1 == position ? TYPE_LOAD : TYPE_REGULAR;

    }

    public static class ViewHolderYoutube extends RecyclerView.ViewHolder {
        View container;
        ImageView videoThumbnail;
        ImageView channelThumbnail;
        TextView title;
        TextView viewsTime;
        Context context;
        SharedPreferences sharedPreferences;


        public ViewHolderYoutube(Context context, View itemView) {
            super(itemView);
            this.context = context;
            container = itemView.findViewById(R.id.item_youtube_container);
            videoThumbnail = (ImageView) itemView.findViewById(R.id.item_youtube_thumbnail);
            channelThumbnail = (ImageView) itemView.findViewById(R.id.item_youtube_header_image);
            title = (TextView) itemView.findViewById(R.id.item_youtube_header_title);
            viewsTime = (TextView) itemView.findViewById(R.id.item_youtube_header_views_time);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        public void bindHolder(final Cursor cursor, final OnYouTubeFullScreenListener onYouTubeFullScreenListener) {
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cursor.moveToPosition(getAdapterPosition());
                    String idVideo = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnYouTube.ID_YOUTUBE));
                    onYouTubeFullScreenListener.onClickYouTubeFullScreenListener(idVideo);
                }
            });
            String urlVideoThumbnail = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnYouTube.THUMBNAILS));
            String urlVideoFullResolution = urlVideoThumbnail.replace("/hqdefault.", "/maxresdefault.");
            LoadImages.setImage(context, urlVideoFullResolution, urlVideoThumbnail, videoThumbnail);
            String urlChannelThumbnail = sharedPreferences.getString(YouTubeTabFragment.TAG_URL_THUMBNAIL_CHANNEL, "");
            Picasso.with(context).load(urlChannelThumbnail)
                    .transform(new CircleTransform()).into(channelThumbnail);
            title.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnYouTube.TITLE_YOUTUBE)));

            String publishedAt = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnYouTube.PUBLISHED_AT));

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String time = "";
            try {
                java.util.Date date = df.parse(publishedAt);
                time = new PrettyTime().format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String views = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnYouTube.VIEW_COUNT));
            viewsTime.setText(context.getString(R.string.youtube_viewTime, views, time));
        }
    }
}
