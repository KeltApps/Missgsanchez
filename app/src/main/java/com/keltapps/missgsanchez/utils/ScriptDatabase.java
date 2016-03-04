package com.keltapps.missgsanchez.utils;

import android.provider.BaseColumns;

/**
 * Created by sergio on 1/01/16 for KelpApps.
 */
public class ScriptDatabase {

    public static final String ENTRY_TABLE_NAME = "entry";
    public static final String PHOTOS_BLOG_TABLE_NAME = "photosBlog";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    public static class ColumnEntries implements BaseColumns {
        public static final String ID = _ID;
        public static final String ID_POST = "id_post";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String TEXT = "text";
        public static final String URL = "url";
    }

    public static final String CREATE_ENTRY =
            "CREATE TABLE " + ENTRY_TABLE_NAME + "(" +
                    ColumnEntries.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnEntries.ID_POST + " " + INT_TYPE + ","+
                    ColumnEntries.TITLE + " " + STRING_TYPE + " not null," +
                    ColumnEntries.DATE + " " + STRING_TYPE + "," +
                    ColumnEntries.TEXT + " " + STRING_TYPE + "," +
                    ColumnEntries.URL + " " + STRING_TYPE + ")";

    public static class ColumnPhotosBlog implements BaseColumns {
        public static final String ID = _ID;
        public static final String ID_POST = "id_post";
        public static final String URL = "url";
    }

    public static final String CREATE_PHOTOS_BLOG =
            "CREATE TABLE " + PHOTOS_BLOG_TABLE_NAME + "(" +
                    ColumnPhotosBlog.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnPhotosBlog.ID_POST + " " + INT_TYPE + "," +
                    ColumnPhotosBlog.URL+ " " + STRING_TYPE + ")";

}
