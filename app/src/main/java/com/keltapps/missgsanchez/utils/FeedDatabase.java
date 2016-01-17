package com.keltapps.missgsanchez.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.keltapps.missgsanchez.models.ItemHttp;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by sergio on 1/01/16 for KelpApps.
 */
public class FeedDatabase extends SQLiteOpenHelper {
    private static final String TAG = FeedDatabase.class.getSimpleName();
    private static final int COLUMN_ID = 0;
    private static final int COLUMN_TITLE = 1;
    private static final int COLUMN_URL = 2;
    private static final int URL_MINIATURE = 3;
    private static final int COLUMN_DATE = 4;
    private static final int COLUMN_DATE_FORMATTED = 5;
    private static final int COLUMN_POST_ID = 6;
    private static final int COLUMN_COMMENT = 7;

    public static int POST_PER_PAGE = 0;
    public static String nextPage = null;
    private boolean start = true;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.ENTRY_TABLE_NAME);
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
     *
     * @param title     title of the entry
     * @param url       url item
     * @param thumb_url url miniature
     * @param post_id   post id
     */
    public void insertEntry(
            String title,
            String url,
            String thumb_url,
            String date,
            String dateFormatted,
            int post_id,
            String comment) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.TITLE, title);
        values.put(ScriptDatabase.ColumnEntries.URL, url);
        values.put(ScriptDatabase.ColumnEntries.URL_MINIATURE, thumb_url);
        values.put(ScriptDatabase.ColumnEntries.DATE, date);
        values.put(ScriptDatabase.ColumnEntries.DATE_FORMATTED, dateFormatted);
        values.put(ScriptDatabase.ColumnEntries.POST_ID, post_id);
        values.put(ScriptDatabase.ColumnEntries.COMMENT, comment);

        getWritableDatabase().insert(
                ScriptDatabase.ENTRY_TABLE_NAME,
                null,
                values
        );
    }


    /**
     * Update the values of the columns in one record
     *
     * @param id        record identifier
     * @param title     new title
     * @param url       new url
     * @param thumb_url new miniature url
     * @param post_id   post id
     */
    public void updateEntry(int id,
                            String title,
                            String url,
                            String thumb_url,
                            String date,
                            String dateFormatted,
                            int post_id,
                            String comment) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.TITLE, title);
        values.put(ScriptDatabase.ColumnEntries.URL, url);
        values.put(ScriptDatabase.ColumnEntries.URL_MINIATURE, thumb_url);
        values.put(ScriptDatabase.ColumnEntries.DATE, date);
        values.put(ScriptDatabase.ColumnEntries.DATE_FORMATTED, dateFormatted);
        values.put(ScriptDatabase.ColumnEntries.POST_ID, post_id);
        values.put(ScriptDatabase.ColumnEntries.COMMENT, comment);

        getWritableDatabase().update(
                ScriptDatabase.ENTRY_TABLE_NAME,
                values,
                ScriptDatabase.ColumnEntries.ID + "=?",
                new String[]{String.valueOf(id)});

    }

    /**
     * @param doc
     * @return true if old posts was download, false otherwise
     */
    public boolean synchronizeEntries(Document doc) {
        boolean oldPost = false;
        Element head = doc.head();
        Elements links = head.select("link");
        nextPage = null;
        for (Element link : links) {
            if (!link.attr("rel").equals("next"))
                continue;
            nextPage = link.attr("href");
        }
        Elements articles = doc.select("article");
        String title = "";
        String link = "";
        String date = "";
        int postId;
        String sPostId;
        String linkMiniature = "";
        String comment = "";
        HashMap<Integer, ItemHttp> entryMap = new HashMap<>();
        for (Element article : articles) {
            sPostId = article.attr("id");
            if (sPostId.equals(""))
                break;
            if (start)
                POST_PER_PAGE++;
            if (sPostId.contains("-"))
                sPostId = sPostId.substring(sPostId.indexOf("-") + 1);
            postId = Integer.parseInt(sPostId);
            for (Element elementDiv : article.select("div")) {
                if (elementDiv.attr("class").equals("blog-hero")) {
                    link = elementDiv.select("a").get(0).attr("href");
                    linkMiniature = elementDiv.select("img").get(0).attr("src");
                } else if (elementDiv.attr("class").equals("post-meta")) {
                    String text = elementDiv.text();
                    if (text.contains(": ") && text.contains("By") && text.contains("With") && text.contains("Comment")) {
                        text = text.substring(text.indexOf(": ") + 2);
                        date = text.substring(0, text.indexOf("By"));
                        comment = text.substring(text.indexOf("With") + 6, text.indexOf("Comment")) + "Comments";
                        if (comment.equals("1 Comments"))
                            comment = comment.substring(0, comment.length() - 1);
                    } else {
                        date = " ";
                        comment = "0 Comments";
                    }
                }
            }
            for (Element elementH3 : article.select("h3")) {
                if (!elementH3.attr("class").equals("entry-title"))
                    continue;
                title = elementH3.select("a").get(0).text();
            }
            entryMap.put(postId, new ItemHttp(title, link, date, formatDate(date), postId, linkMiniature, comment));
        }
        start = false;
        Cursor c = getEntries();
        assert c != null;


        while (c.moveToNext()) {
            postId = c.getInt(COLUMN_POST_ID);
            ItemHttp match = entryMap.get(postId);
            if (match != null) {
                entryMap.remove(postId);
                oldPost = true;
                if (match.getPostId() != postId) {
                    updateEntry(
                            c.getInt(COLUMN_ID),
                            match.getTitle(),
                            match.getLink(),
                            match.getLinkMiniature(),
                            match.getDate(),
                            match.getDateFormatted(),
                            postId,
                            match.getComment()
                    );

                }
            }
        }
        c.close();
        for (ItemHttp article : entryMap.values()) {
            Log.i(TAG, "insert article");
            insertEntry(
                    article.getTitle(),
                    article.getLink(),
                    article.getLinkMiniature(),
                    article.getDate(),
                    article.getDateFormatted(),
                    article.getPostId(),
                    article.getComment()
            );
        }
        return oldPost;
    }

    private String formatDate(String date) {
        String month = date.substring(0, date.indexOf(" "));
        String day = date.substring(date.indexOf(" ") + 1, date.indexOf(","));
        if (day.length() == 1)
            day = "0" + day;
        String year = date.substring(date.indexOf(",") + 2, date.lastIndexOf(" "));
        if (month.equals("January"))
            month = "01";
        else if (month.equals("February"))
            month = "02";
        else if (month.equals("March"))
            month = "03";
        else if (month.equals("April"))
            month = "04";
        else if (month.equals("May"))
            month = "05";
        else if (month.equals("June"))
            month = "06";
        else if (month.equals("July"))
            month = "07";
        else if (month.equals("August"))
            month = "08";
        else if (month.equals("September"))
            month = "09";
        else if (month.equals("October"))
            month = "10";
        else if (month.equals("November"))
            month = "11";
        else if (month.equals("December"))
            month = "12";
        return year + "-" + month + "-" + day;
    }
}
