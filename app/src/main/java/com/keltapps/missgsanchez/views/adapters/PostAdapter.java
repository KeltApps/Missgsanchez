package com.keltapps.missgsanchez.views.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;


public class PostAdapter extends CursorRecyclerViewAdapter<PostAdapter.ViewHolderPost> {
    private Context context;

    public PostAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        Log.v("prueba", "Adapter");
        this.context = context;
    }

    @Override
    public ViewHolderPost onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v("prueba", "on create view holder");
        return new ViewHolderPost(context, LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item_blog, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolderPost viewHolderPost, Cursor cursor) {
        Log.v("prueba", "on bind view holder");
        viewHolderPost.bindProfile(cursor);
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

        public void bindProfile(Cursor cursor) {
            String urlMiniature = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.URL_MINIATURE));
            if (urlMiniature.contains("-"))
                urlMiniature = urlMiniature.substring(0, urlMiniature.lastIndexOf("-")) + urlMiniature.substring(urlMiniature.lastIndexOf("."));
            Picasso.with(context)
                    .load(urlMiniature)
                    .fit()
                    .centerCrop()
                    .into(imageView);
            title.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.TITLE)));
            time.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnEntries.DATE)));
            comments.setText("2");

        }

        private String differenceDate(Date date) {
            return "2 hours ago";
        }
    }
}
