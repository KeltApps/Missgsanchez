package com.keltapps.missgsanchez.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.views.BlogItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolderPost> {
    private ArrayList<BlogItem> listPost;
    private Context context;

    public PostAdapter(Context context, ArrayList<BlogItem> list) {
        this.context = context;
        listPost = list;
    }

    @Override
    public ViewHolderPost onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolderPost(context,LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item_blog, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolderPost viewHolderPost, int i) {
        BlogItem item = listPost.get(i);
        viewHolderPost.bindProfile(item);
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public static class ViewHolderPost extends RecyclerView.ViewHolder {
        Context context;
        ImageView imageView;
        TextView title;
        TextView time;
        TextView comments;
        Calendar calendar;
        int resizeHeight;

        public ViewHolderPost(Context context,View itemView) {
            super(itemView);
            this.context = context;
            imageView = (ImageView) itemView.findViewById(R.id.item_blog_imageView);
            title = (TextView) itemView.findViewById(R.id.linear_title_textView);
            time = (TextView) itemView.findViewById(R.id.extraInformation_textView_time);
            comments = (TextView) itemView.findViewById(R.id.extraInformation_textView_comments);
            calendar = Calendar.getInstance();
             resizeHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,600,
                     context.getResources().getDisplayMetrics());
        }

        public void bindProfile(final BlogItem item) {

            Picasso.with(context)
                    .load(item.getImageLink())
                    .fit()
                    .centerCrop()
                    .into(imageView);
            title.setText(item.getTittle());
            time.setText(differenceDate(item.getDatePost()));
            comments.setText(Integer.toString(item.getCounterComments()));


        }


        private String differenceDate(Date date) {
            return "2 hours ago";
        }
    }
}
