package com.keltapps.missgsanchez.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.keltapps.missgsanchez.models.BlogItem;

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

/**
 * Created by sergio on 1/01/16 for KelpApps.
 */
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
        db.execSQL(ScriptDatabase.CREATE_ENTRY);
        db.execSQL(ScriptDatabase.CREATE_PHOTOS_BLOG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.ENTRY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.PHOTOS_BLOG_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Get all the records of the table entry
     *
     * @return cursor with the records
     */
    public Cursor getEntries() {
        return getWritableDatabase().rawQuery(
                "select * from " + ScriptDatabase.ENTRY_TABLE_NAME, null);
    }

    /**
     * Insert a record in table entry
     */
    public void insertEntry(
            int idPost,
            String title,
            String date,
            String textEs,
            String textEn) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.ID_POST, idPost);
        values.put(ScriptDatabase.ColumnEntries.TITLE, title);
        values.put(ScriptDatabase.ColumnEntries.DATE, date);
        values.put(ScriptDatabase.ColumnEntries.TEXT_ES, textEs);
        values.put(ScriptDatabase.ColumnEntries.TEXT_EN, textEn);

        getWritableDatabase().insert(
                ScriptDatabase.ENTRY_TABLE_NAME,
                null,
                values
        );
    }


    public void updateEntry(int id,
                            int idPost,
                            String title,
                            String date,
                            String textEs,
                            String textEn) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.ID_POST, idPost);
        values.put(ScriptDatabase.ColumnEntries.TITLE, title);
        values.put(ScriptDatabase.ColumnEntries.DATE, date);
        values.put(ScriptDatabase.ColumnEntries.TEXT_ES, textEs);
        values.put(ScriptDatabase.ColumnEntries.TEXT_EN, textEn);

        getWritableDatabase().update(
                ScriptDatabase.ENTRY_TABLE_NAME,
                values,
                ScriptDatabase.ColumnEntries.ID + "=?",
                new String[]{String.valueOf(id)});

    }


    public int synchronizeEntries(String data) {
        if(data.equals(TAG_EMPTY_PAGE))
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
            int postId = c.getInt(c.getColumnIndex(ScriptDatabase.ColumnEntries.ID_POST));
            BlogItem match = entryMap.get(postId);
            if (match != null) {
                entryMap.remove(postId);
                oldPost = true;
                if (match.getIdPost() != postId) {
                    updateEntry(
                            c.getInt(c.getColumnIndex(ScriptDatabase.ColumnEntries.ID)),
                            match.getIdPost(),
                            match.getJsonRenderedTitle().getString(),
                            match.getDatePost(),
                            match.getJsonRenderedContent().getString(),
                            match.getJsonRenderedContent().getString()
                    );

                }
            }
        }
        c.close();
        for (BlogItem article : entryMap.values()) {
            Log.i(TAG, "insert article");
            insertEntry(
                    article.getIdPost(),
                    article.getJsonRenderedTitle().getString(),
                    article.getDatePost(),
                    article.getJsonRenderedContent().getString(),
                    article.getJsonRenderedContent().getString()
            );
            synchronizePhotosBlog(article.getIdPost(), article.getJsonRenderedContent().getString());
        }

        return oldPost ? RETURN_OLD_POST : RETURN_NO_OLD_POST;
    }

    /**
     * Insert a record in table photoBlog
     */
    public void insertPhotoBlog(
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

    public Cursor getPhotosBlogId(int idPost) {
        return getWritableDatabase().rawQuery(
                "select * from " + ScriptDatabase.PHOTOS_BLOG_TABLE_NAME + " where " + ScriptDatabase.ColumnPhotosBlog.ID_POST + " = " + idPost, null);
    }

    private void synchronizePhotosBlog(int idPost, String data) {
        Document dataParsed = Jsoup.parse(data);
        Elements elementsPhotos = dataParsed.select("img");
        List<String> listPhotos = new LinkedList<>();
        for (Element element : elementsPhotos) {
            String urlPhoto = element.attr("src");
            Log.v("prueba", "PHOTO FOUNDED: " + urlPhoto);
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

}
