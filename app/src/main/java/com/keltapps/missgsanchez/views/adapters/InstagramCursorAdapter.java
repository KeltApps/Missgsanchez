package com.keltapps.missgsanchez.views.adapters;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.CircleTransform;
import com.keltapps.missgsanchez.views.MyVideoView;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class InstagramCursorAdapter extends CursorAdapter implements StickyListHeadersAdapter {
    private final Context context;
    private Cursor mCursor;

    public InstagramCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
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
        String urlOriginal = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnInstagram.URL_PHOTO));
        String urlFullResolution = urlOriginal.replace("/s640x640", "");
        LoadImages.setImage(context, urlFullResolution, urlOriginal, photo);
        TextView likes = (TextView) view.findViewById(R.id.item_instagram_body_textView_likes);
        likes.setText(context.getString(R.string.instagram_likes, cursor.getInt(cursor.getColumnIndex(ScriptDatabase.ColumnInstagram.LIKES))));
        TextView title = (TextView) view.findViewById(R.id.item_instagram_body_textView_title);

        SpannableString hashText = new SpannableString(cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnInstagram.TITLE)));
        Matcher matcher = Pattern.compile("(#\\w+)|(@\\w+)").matcher(hashText);
        while (matcher.find()) {
            hashText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent_disabled)), matcher.start(), matcher.end(), 0);

        }
        title.setText(hashText);
        ViewGroup frameLayout = (ViewGroup) view.findViewById(R.id.item_instagram_body_frameLayout);
        String urlVideo = cursor.getString(cursor.getColumnIndex(ScriptDatabase.ColumnInstagram.URL_VIDEO));
        if (urlVideo != null) {
            final MyVideoView video = new MyVideoView(context);
            frameLayout.addView(video);
            video.setVideoURI(Uri.parse(urlVideo));
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("AAA", "onClick: ");
                    video.exchangeVolume();
                }
            });
        } else {
            if (frameLayout.getChildCount() == 2)
                frameLayout.removeViewAt(1);
        }
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        ViewHolderHeader holder;
        mCursor.moveToPosition(position);
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
        Picasso.with(context).load(mCursor.getString(mCursor.getColumnIndex(ScriptDatabase.ColumnInstagram.URL_PROFILE_PHOTO)))
                .transform(new CircleTransform()).into(holder.profilePhoto);
        String user = mCursor.getString(mCursor.getColumnIndex(ScriptDatabase.ColumnInstagram.USER));
        String location = mCursor.getString(mCursor.getColumnIndex(ScriptDatabase.ColumnInstagram.LOCATION));
        if (location.equals(""))
            holder.userLocation.setText(user);
        else {
            Spannable wordToSpan = new SpannableString(context.getString(R.string.instagram_userLocation, user, location));
            wordToSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorLink)), user.length(), wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.userLocation.setText(wordToSpan);
        }
        long time = mCursor.getInt(mCursor.getColumnIndex(ScriptDatabase.ColumnInstagram.TIME));

        Date date = new Date(time * 1000);
        holder.time.setText(new PrettyTime().format(date));
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

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        return super.swapCursor(newCursor);
    }

}
