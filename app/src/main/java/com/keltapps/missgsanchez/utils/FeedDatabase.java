package com.keltapps.missgsanchez.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.keltapps.missgsanchez.models.ItemRss;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sergio on 1/01/16 for KelpApps.
 */
public class FeedDatabase extends SQLiteOpenHelper {
    private static final String TAG = FeedDatabase.class.getSimpleName();
    private static final int COLUMN_ID = 0;
    private static final int COLUMN_TITLE = 1;
    private static final int COLUMN_DESC = 2;
    private static final int COLUMN_URL = 3;

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
     * @param title       title of the entry
     * @param description description of the entry
     * @param url         url item
     * @param thumb_url   url miniature
     */
    public void insertEntry(
            String title,
            String description,
            String url,
            String thumb_url,
            String date) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.TITLE, title);
        values.put(ScriptDatabase.ColumnEntries.DESCRIPTION, description);
        values.put(ScriptDatabase.ColumnEntries.URL, url);
        values.put(ScriptDatabase.ColumnEntries.URL_MINIATURE, thumb_url);
        values.put(ScriptDatabase.ColumnEntries.DATE, date);

        getWritableDatabase().insert(
                ScriptDatabase.ENTRY_TABLE_NAME,
                null,
                values
        );
    }


    /**
     * Update the values of the columns in one record
     *
     * @param id          record identifier
     * @param title       new title
     * @param description new description
     * @param url         new url
     * @param thumb_url   new miniature url
     */
    public void updateEntry(int id,
                            String title,
                            String description,
                            String url,
                            String thumb_url,
                            String date) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.TITLE, title);
        values.put(ScriptDatabase.ColumnEntries.DESCRIPTION, description);
        values.put(ScriptDatabase.ColumnEntries.URL, url);
        values.put(ScriptDatabase.ColumnEntries.URL_MINIATURE, thumb_url);
        values.put(ScriptDatabase.ColumnEntries.DATE, date);

        getWritableDatabase().update(
                ScriptDatabase.ENTRY_TABLE_NAME,
                values,
                ScriptDatabase.ColumnEntries.ID + "=?",
                new String[]{String.valueOf(id)});

    }

    public void sincronizarEntradas(List<ItemRss> entries) {
        HashMap<String, ItemRss> entryMap = new HashMap<>();
        for (ItemRss e : entries)
            entryMap.put(e.getTitle(), e);

        Cursor c = getEntries();
        assert c != null;
        int id;
        String title;
        String description;
        String url;

        while (c.moveToNext()) {

            id = c.getInt(COLUMN_ID);
            title = c.getString(COLUMN_TITLE);
            description = c.getString(COLUMN_DESC);
            url = c.getString(COLUMN_URL);

            ItemRss match = entryMap.get(title);
            if (match != null) {
                entryMap.remove(title);

                if ((match.getTitle() != null && !match.getTitle().equals(title)) ||
                        (match.getDescription() != null && !match.getDescription().equals(description)) ||
                        (match.getLink() != null && !match.getLink().equals(url))) {

                    updateEntry(
                            id,
                            match.getTitle(),
                            match.getDescription(),
                            match.getLink(),
                            match.getContent().getUrl(),
                            formatDate(match.getDate())
                    );

                }
            }
        }
        c.close();
        for (ItemRss e : entryMap.values()) {
            insertEntry(
                    e.getTitle(),
                    e.getDescription(),
                    e.getLink(),
                    e.getContent().getUrl(),
                    formatDate(e.getDate())
            );
        }
    }

    private String formatDate(String date) {
        date = date.substring(date.indexOf(",") + 2);
        String day = date.substring(0, 2);
        date = date.substring(3);
        String month = date.substring(0, 3);
        date = date.substring(4);
        String year = date.substring(0, 4);
        date = date.substring(5);
        String time = date.substring(0, 8);
        if (month.equals("Jan"))
            month = "01";
        else if (month.equals("Feb"))
            month = "02";
        else if (month.equals("Mar"))
            month = "03";
        else if (month.equals("Apr"))
            month = "04";
        else if (month.equals("May"))
            month = "05";
        else if (month.equals("Jun"))
            month = "06";
        else if (month.equals("Jul"))
            month = "07";
        else if (month.equals("Aug"))
            month = "08";
        else if (month.equals("Sep"))
            month = "09";
        else if (month.equals("Oct"))
            month = "10";
        else if (month.equals("Nov"))
            month = "11";
        else if (month.equals("Dec"))
            month = "12";
        return year+"-"+month+"-"+day+" "+time;
    }
}
