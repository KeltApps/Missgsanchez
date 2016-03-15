package com.keltapps.missgsanchez.views.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.CircleTransform;
import com.squareup.picasso.Picasso;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class InstagramCursorAdapter extends CursorAdapter implements StickyListHeadersAdapter {
    private static final int TYPE_REGULAR = 0;
    private static final int TYPE_LOAD = 1;
    Context context;
    Cursor mCursor;

    public InstagramCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
        mCursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_instagram_body, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        ImageView photo = (ImageView) view.findViewById(R.id.item_instagram_body_imageView);
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnInstagram.URL_PHOTO))).into(photo);
        TextView likes = (TextView) view.findViewById(R.id.item_instagram_body_textView_likes);
        likes.setText(context.getString(R.string.instagram_likes, cursor.getInt(cursor.getColumnIndex(ScriptDatabase.ColumnInstagram.LIKES))));
        TextView title = (TextView) view.findViewById(R.id.item_instagram_body_textView_title);
        title.setText(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnInstagram.TITLE)));
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        ViewHolderHeader holder;
        if (convertView == null) {
            holder = new ViewHolderHeader();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_instagram_header, parent, false);
            holder.profilePhoto = (ImageView) convertView.findViewById(R.id.item_instagram_header_image);
            holder.userLocation = (TextView) convertView.findViewById(R.id.item_instagram_header_user_location);
            holder.time = (TextView) convertView.findViewById(R.id.item_instagram_header_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderHeader) convertView.getTag();
        }
        mCursor.moveToPosition(position);
        Picasso.with(context).load(mCursor.getString(mCursor.getColumnIndex(ScriptDatabase.ColumnInstagram.URL_PROFILE_PHOTO)))
                .transform(new CircleTransform()).into(holder.profilePhoto);
        String user = mCursor.getString(mCursor.getColumnIndex(ScriptDatabase.ColumnInstagram.USER));
        String location = mCursor.getString(mCursor.getColumnIndex(ScriptDatabase.ColumnInstagram.LOCATION));
        holder.userLocation.setText(context.getString(R.string.instagram_userLocation, user, location));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    private class ViewHolderHeader {
        ImageView profilePhoto;
        TextView userLocation;
        TextView time;
    }

}
