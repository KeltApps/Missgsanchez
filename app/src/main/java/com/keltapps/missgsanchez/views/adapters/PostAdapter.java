package com.keltapps.missgsanchez.views.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.models.ViewHolderLoad;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.utils.FeedDatabase;
import com.keltapps.missgsanchez.utils.ScriptDatabase;

import java.util.Calendar;


public class PostAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static final int TYPE_REGULAR = 0;
    private static final int TYPE_LOAD = 1;
    private Context context;

    public PostAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        switch (getItemViewType(cursor.getPosition())) {
            case TYPE_REGULAR:
                ((ViewHolderPost) viewHolder).bindProfile(context, cursor);
                break;
            case TYPE_LOAD:
                ((ViewHolderLoad) viewHolder).bindProfile();
                break;
            default:
                ((ViewHolderPost) viewHolder).bindProfile(context, cursor);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case TYPE_REGULAR:
                return new ViewHolderPost(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.post_item_blog, viewGroup, false));
            case TYPE_LOAD:
                return new ViewHolderLoad(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_load_more, viewGroup, false));
            default:
                return new ViewHolderPost(context, LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.post_item_blog, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(getCursor() == null || FeedDatabase.nextPage == null)
            return  TYPE_REGULAR;
        return getCursor().getCount() - 1 == position ? TYPE_LOAD : TYPE_REGULAR;

    }

    public static class ViewHolderPost extends RecyclerView.ViewHolder {
        Context context;
        ImageView imageView;
        TextView title;
        TextView time;
        TextView comments;
        Calendar calendar;
        int resizeHeight;

        public ViewHolderPost(Context context, View itemView) {
            super(itemView);
            this.context = context;
            imageView = (ImageView) itemView.findViewById(R.id.item_blog_imageView);
            title = (TextView) itemView.findViewById(R.id.linear_title_textView);
            time = (TextView) itemView.findViewById(R.id.extraInformation_textView_time);
            comments = (TextView) itemView.findViewById(R.id.extraInformation_textView_comments);
            calendar = Calendar.getInstance();
            resizeHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600,
                    context.getResources().getDisplayMetrics());
        }

        public void bindProfile(final Context context, Cursor cursor) {
            String urlMiniature, urlMiniatureFullResolution, urlMiniatureName;
            urlMiniature = urlMiniatureFullResolution = urlMiniatureName = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.URL_MINIATURE));
            if (urlMiniature.contains("/"))
                urlMiniatureName = urlMiniature.substring(urlMiniature.lastIndexOf("/"));
            if (urlMiniatureName.contains("-"))
                urlMiniatureFullResolution = urlMiniature.substring(0, urlMiniature.lastIndexOf("/")) + urlMiniatureName.substring(0, urlMiniatureName.lastIndexOf("-")) + urlMiniature.substring(urlMiniature.lastIndexOf("."));
            LoadImages.setImage(context, urlMiniatureFullResolution, urlMiniature, imageView);
            title.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.TITLE)));
            time.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.DATE)));
            comments.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.COMMENT)));
        }
    }
}
