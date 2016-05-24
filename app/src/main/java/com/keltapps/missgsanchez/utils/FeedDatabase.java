package com.keltapps.missgsanchez.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.keltapps.missgsanchez.models.Blog.BlogItem;
import com.keltapps.missgsanchez.models.Instagram.InstagramItem;
import com.keltapps.missgsanchez.models.Instagram.InstagramSubItem;
import com.keltapps.missgsanchez.models.YouTube.YouTubeSearchChannelItem;
import com.keltapps.missgsanchez.models.YouTube.YouTubeSearchChannelSubItem;
import com.keltapps.missgsanchez.models.YouTube.YouTubeSearchItem;
import com.keltapps.missgsanchez.models.YouTube.YouTubeSearchSubItem;
import com.keltapps.missgsanchez.models.YouTube.YouTubeVideoItem;
import com.keltapps.missgsanchez.models.YouTube.YouTubeVideoSubItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class FeedDatabase extends SQLiteOpenHelper {
    private static final String TAG = FeedDatabase.class.getSimpleName();
    private static final String TAG_EMPTY_PAGE = "[]";
    public static final int RETURN_NO_OLD_POST = 0;
    public static final int RETURN_OLD_POST = 1;
    public static final int RETURN_EMPTY_PAGE = 2;

    public static final String DATABASE_NAME = "Feed.db";

    public static final int DATABASE_VERSION = 1;

    private static FeedDatabase ourInstance;

    public static synchronized FeedDatabase getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new FeedDatabase(context.getApplicationContext());
        return ourInstance;
    }

    private FeedDatabase(Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDatabase.CREATE_BLOG);
        db.execSQL(ScriptDatabase.CREATE_PHOTOS_BLOG);
        db.execSQL(ScriptDatabase.CREATE_INSTAGRAM);
        db.execSQL(ScriptDatabase.CREATE_YOUTUBE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.BLOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.PHOTOS_BLOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.INSTAGRAM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.YOUTUBE_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Get all the records of the table entry
     *
     * @return cursor with the records
     */
    public Cursor getEntries() {
        return getWritableDatabase().rawQuery(
                "select * from " + ScriptDatabase.BLOG_TABLE_NAME, null);
    }

    /**
     * Insert a record in table entry
     */
    private void insertEntry(
            int idPost,
            String title,
            String date,
            String textEs,
            String url) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnBlog.ID_POST, idPost);
        values.put(ScriptDatabase.ColumnBlog.TITLE, title);
        values.put(ScriptDatabase.ColumnBlog.DATE, date);
        values.put(ScriptDatabase.ColumnBlog.TEXT, textEs);
        values.put(ScriptDatabase.ColumnBlog.URL, url);

        getWritableDatabase().insert(
                ScriptDatabase.BLOG_TABLE_NAME,
                null,
                values
        );
    }


    private void updateEntry(int id,
                             int idPost,
                             String title,
                             String date,
                             String textEs,
                             String url) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnBlog.ID_POST, idPost);
        values.put(ScriptDatabase.ColumnBlog.TITLE, title);
        values.put(ScriptDatabase.ColumnBlog.DATE, date);
        values.put(ScriptDatabase.ColumnBlog.TEXT, textEs);
        values.put(ScriptDatabase.ColumnBlog.URL, url);

        getWritableDatabase().update(
                ScriptDatabase.BLOG_TABLE_NAME,
                values,
                ScriptDatabase.ColumnBlog.ID + "=?",
                new String[]{String.valueOf(id)});

    }


    public int synchronizeEntries(String data) {
        if (data.equals(TAG_EMPTY_PAGE))
            return RETURN_EMPTY_PAGE;
        Type fooType = new TypeToken<ArrayList<BlogItem>>() {
        }.getType();
        List<BlogItem> listBlogItems = new GsonBuilder().create().fromJson(data, fooType);
        HashMap<Integer, BlogItem> entryMap = new HashMap<>();
        for (BlogItem blogItem : listBlogItems)
            entryMap.put(blogItem.getIdPost(), blogItem);

        Cursor c = getEntries();
        assert c != null;
        Boolean oldPost = false;
        while (c.moveToNext()) {
            int postId = c.getInt(c.getColumnIndex(ScriptDatabase.ColumnBlog.ID_POST));
            BlogItem match = entryMap.get(postId);
            if (match != null) {
                entryMap.remove(postId);
                oldPost = true;
                if (match.getIdPost() != postId) {
                    updateEntry(
                            c.getInt(c.getColumnIndex(ScriptDatabase.ColumnBlog.ID)),
                            match.getIdPost(),
                            match.getBlogRenderedTitle().getString(),
                            match.getDatePost(),
                            match.getBlogRenderedContent().getString(),
                            match.getUrl()
                    );

                }
            }
        }
        c.close();
        for (BlogItem article : entryMap.values()) {
            //    Log.i(TAG, "insert article: " + article.getBlogRenderedContent().getString());
            insertEntry(
                    article.getIdPost(),
                    article.getBlogRenderedTitle().getString(),
                    article.getDatePost(),
                    article.getBlogRenderedContent().getString(),
                    article.getUrl()
            );
            synchronizePhotosBlog(article.getIdPost(), article.getBlogRenderedContent().getString());
        }

        return oldPost ? RETURN_OLD_POST : RETURN_NO_OLD_POST;
    }

    /**
     * Insert a record in table photoBlog
     */
    private void insertPhotoBlog(
            int idPost,
            String urlPhoto) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnPhotosBlog.ID_POST, idPost);
        values.put(ScriptDatabase.ColumnPhotosBlog.URL, urlPhoto);

        getWritableDatabase().insert(
                ScriptDatabase.PHOTOS_BLOG_TABLE_NAME,
                null,
                values
        );
    }

    private Cursor getPhotosBlogId(int idPost) {
        return getWritableDatabase().rawQuery(
                "select * from " + ScriptDatabase.PHOTOS_BLOG_TABLE_NAME + " where " + ScriptDatabase.ColumnPhotosBlog.ID_POST + " = " + idPost, null);
    }

    private void synchronizePhotosBlog(int idPost, String data) {
        Document dataParsed = Jsoup.parse(data);
        Elements elementsPhotos = dataParsed.select("img");
        List<String> listPhotos = new LinkedList<>();
        for (Element element : elementsPhotos) {
            String urlPhoto = element.attr("src");
            if (!urlPhoto.isEmpty())
                listPhotos.add(element.attr("src"));
        }
        Cursor c = getPhotosBlogId(idPost);
        assert c != null;
        while (c.moveToNext()) {
            Iterator<String> iterator = listPhotos.iterator();
            while (iterator.hasNext()) {
                String urlPhoto = iterator.next();
                if (c.getString(c.getColumnIndex(ScriptDatabase.ColumnPhotosBlog.URL)).equals(urlPhoto))
                    iterator.remove();
            }
        }
        c.close();

        for (String urlPhoto : listPhotos)
            insertPhotoBlog(idPost, urlPhoto);
    }


    private void insertInstagram(String idPost, String urlProfilePhoto, String user, String location,
                                 int time, String urlPhoto, String urlVideo, int likes, String title) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnInstagram.ID_POST, idPost);
        values.put(ScriptDatabase.ColumnInstagram.URL_PROFILE_PHOTO, urlProfilePhoto);
        values.put(ScriptDatabase.ColumnInstagram.USER, user);
        values.put(ScriptDatabase.ColumnInstagram.LOCATION, location);
        values.put(ScriptDatabase.ColumnInstagram.TIME, time);
        values.put(ScriptDatabase.ColumnInstagram.URL_PHOTO, urlPhoto);
        values.put(ScriptDatabase.ColumnInstagram.URL_VIDEO, urlVideo);
        values.put(ScriptDatabase.ColumnInstagram.LIKES, likes);
        values.put(ScriptDatabase.ColumnInstagram.TITLE, title);

        getWritableDatabase().insert(
                ScriptDatabase.INSTAGRAM_TABLE_NAME,
                null,
                values
        );
    }

    private void updateInstagram(int id, String idPost, String urlProfilePhoto, String user, String location,
                                 int time, String urlPhoto, String urlVideo, int likes, String title) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnInstagram.ID_POST, idPost);
        values.put(ScriptDatabase.ColumnInstagram.URL_PROFILE_PHOTO, urlProfilePhoto);
        values.put(ScriptDatabase.ColumnInstagram.USER, user);
        values.put(ScriptDatabase.ColumnInstagram.LOCATION, location);
        values.put(ScriptDatabase.ColumnInstagram.TIME, time);
        values.put(ScriptDatabase.ColumnInstagram.URL_PHOTO, urlPhoto);
        values.put(ScriptDatabase.ColumnInstagram.URL_VIDEO, urlVideo);
        values.put(ScriptDatabase.ColumnInstagram.LIKES, likes);
        values.put(ScriptDatabase.ColumnInstagram.TITLE, title);

        getWritableDatabase().update(
                ScriptDatabase.INSTAGRAM_TABLE_NAME,
                values,
                ScriptDatabase.ColumnBlog.ID + "=?",
                new String[]{String.valueOf(id)});

    }


    private Cursor getInstagram() {
        return getWritableDatabase().rawQuery(
                "select * from " + ScriptDatabase.INSTAGRAM_TABLE_NAME, null);
    }


    public int synchronizeInstagram(String data) {
        int intReturn = RETURN_NO_OLD_POST;
        Type fooType = new TypeToken<InstagramItem>() {
        }.getType();
        InstagramItem instagramItem = new GsonBuilder().create().fromJson(data, fooType);
        if (!instagramItem.isMoreAvailable())
            return RETURN_EMPTY_PAGE;
        HashMap<String, InstagramSubItem> entryMap = new HashMap<>();
        List<InstagramSubItem> instagramSubItems = instagramItem.getListSubItem();
        for (InstagramSubItem instagramSubItem : instagramSubItems)
            entryMap.put(instagramSubItem.getId(), instagramSubItem);
        Cursor c = getInstagram();
        assert c != null;

        while (c.moveToNext()) {
            String postId = c.getString(c.getColumnIndex(ScriptDatabase.ColumnInstagram.ID_POST));
            InstagramSubItem match = entryMap.get(postId);
            if (match != null) {
                entryMap.remove(postId);
                intReturn = RETURN_OLD_POST;
                if (!match.getId().equals(postId)) {
                    String urlVideo = null;
                    if (match.getInstagramVideos() != null)
                        urlVideo = match.getInstagramVideos().getStandard_resolution().getUrl();
                    updateInstagram(
                            c.getInt(c.getColumnIndex(ScriptDatabase.ColumnInstagram.ID)),
                            match.getId(),
                            match.getInstagramUser().getProfilePicture(),
                            match.getInstagramUser().getUserName(),
                            match.getLocation().getName(),
                            match.getTime(),
                            match.getInstagramImages().getStandard_resolution().getUrl(),
                            urlVideo,
                            match.getInstagramLikes().getCount(),
                            match.getInstagramCaption().getTitle()
                    );

                }
            }
        }
        c.close();
        for (InstagramSubItem article : entryMap.values()) {
            String location = "";
            if (article.getLocation() != null)
                location = article.getLocation().getName();
            String urlVideo = null;
            if (article.getInstagramVideos() != null)
                urlVideo = article.getInstagramVideos().getStandard_resolution().getUrl();
            insertInstagram(
                    article.getId(),
                    article.getInstagramUser().getProfilePicture(),
                    article.getInstagramUser().getUserName(),
                    location,
                    article.getTime(),
                    article.getInstagramImages().getStandard_resolution().getUrl(),
                    urlVideo,
                    article.getInstagramLikes().getCount(),
                    article.getInstagramCaption().getTitle()
            );
        }
        return intReturn;
    }


    private void insertYouTube(String idYouTube, String publishedAt, String title, String thumbnails,
                               String description, String duration, int viewCount, int likeCount) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnYouTube.ID_YOUTUBE, idYouTube);
        values.put(ScriptDatabase.ColumnYouTube.PUBLISHED_AT, publishedAt);
        values.put(ScriptDatabase.ColumnYouTube.TITLE_YOUTUBE, title);
        values.put(ScriptDatabase.ColumnYouTube.THUMBNAILS, thumbnails);
        values.put(ScriptDatabase.ColumnYouTube.DESCRIPTION, description);
        values.put(ScriptDatabase.ColumnYouTube.DURATION, duration);
        values.put(ScriptDatabase.ColumnYouTube.VIEW_COUNT, viewCount);
        values.put(ScriptDatabase.ColumnYouTube.LIKE_COUNT, likeCount);

        getWritableDatabase().insert(
                ScriptDatabase.YOUTUBE_TABLE_NAME,
                null,
                values
        );
    }

    private void updateYouTube(int id, String idYouTube, String publishedAt, String title, String thumbnails,
                               String description, String duration, int viewCount, int likeCount) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnYouTube.ID_YOUTUBE, idYouTube);
        values.put(ScriptDatabase.ColumnYouTube.PUBLISHED_AT, publishedAt);
        values.put(ScriptDatabase.ColumnYouTube.TITLE_YOUTUBE, title);
        values.put(ScriptDatabase.ColumnYouTube.THUMBNAILS, thumbnails);
        values.put(ScriptDatabase.ColumnYouTube.DESCRIPTION, description);
        values.put(ScriptDatabase.ColumnYouTube.DURATION, duration);
        values.put(ScriptDatabase.ColumnYouTube.VIEW_COUNT, viewCount);
        values.put(ScriptDatabase.ColumnYouTube.LIKE_COUNT, likeCount);

        getWritableDatabase().update(
                ScriptDatabase.YOUTUBE_TABLE_NAME,
                values,
                ScriptDatabase.ColumnBlog.ID + "=?",
                new String[]{String.valueOf(id)});

    }


    private Cursor getYouTube() {
        return getWritableDatabase().rawQuery(
                "select * from " + ScriptDatabase.YOUTUBE_TABLE_NAME, null);
    }


    public List<String> synchronizeYouTubeSearch(String data) {
        List<String> returnList = new LinkedList<>();
        Type fooType = new TypeToken<YouTubeSearchItem>() {
        }.getType();
        YouTubeSearchItem youTubeSearchItem = new GsonBuilder().create().fromJson(data, fooType);
        HashMap<String, YouTubeSearchSubItem> entryMap = new HashMap<>();
        List<YouTubeSearchSubItem> youTubeSearchSubItems = youTubeSearchItem.getYouTubeSearchSubItems();
        for (YouTubeSearchSubItem youTubeSearchSubItem : youTubeSearchSubItems)
            entryMap.put(youTubeSearchSubItem.getId().getVideoId(), youTubeSearchSubItem);
        Cursor c = getYouTube();
        assert c != null;
        while (c.moveToNext()) {
            String idYoutube = c.getString(c.getColumnIndex(ScriptDatabase.ColumnYouTube.ID_YOUTUBE));
            YouTubeSearchSubItem match = entryMap.get(idYoutube);
            if (match != null)
                entryMap.remove(idYoutube);
        }
        c.close();
        for (YouTubeSearchSubItem youTubeSearchSubItem : entryMap.values()) {
            returnList.add(youTubeSearchSubItem.getId().getVideoId());
        }
        return returnList;
    }

    public String synchronizeYouTubeVideo(String data) {
        String publishedAt = null;
        Type fooType = new TypeToken<YouTubeVideoItem>() {
        }.getType();
        YouTubeVideoItem youTubeVideoItem = new GsonBuilder().create().fromJson(data, fooType);
        List<YouTubeVideoSubItem> youTubeVideoSubItems = youTubeVideoItem.getYouTubeVideoSubItems();
        for (YouTubeVideoSubItem article : youTubeVideoSubItems) {
            insertYouTube(
                    article.getIdYouTube(),
                    article.getYouTubeSnippet().getPublishedAt(),
                    article.getYouTubeSnippet().getTitle(),
                    article.getYouTubeSnippet().getYouTubeThumbnails().getYouTubeThumbnailsHigh().getUrl(),
                    article.getYouTubeSnippet().getDescription(),
                    article.getYouTubeContentDetails().getDuration(),
                    article.getYouTubeStatistics().getViewCount(),
                    article.getYouTubeStatistics().getLikeCount()
            );
        }
        return publishedAt;
    }

    public String synchronizeYouTubeSearchChannel(String data) {
        Type fooType = new TypeToken<YouTubeSearchChannelItem>() {
        }.getType();
        YouTubeSearchChannelItem youTubeSearchChannelItem = new GsonBuilder().create().fromJson(data, fooType);
        List<YouTubeSearchChannelSubItem> youTubeSearchChannelSubItem = youTubeSearchChannelItem.getYouTubeSearchChannelSubItems();
        if (youTubeSearchChannelSubItem.isEmpty())
            return "";
        return youTubeSearchChannelSubItem.get(0).getYouTubeSnippet().getYouTubeThumbnails().getYouTubeThumbnailsHigh().getUrl();
    }

}
