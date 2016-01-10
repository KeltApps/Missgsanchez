package com.keltapps.missgsanchez.utils;

import android.provider.BaseColumns;

/**
 * Created by sergio on 1/01/16 for KelpApps.
 */
public class ScriptDatabase {

    public static final String ENTRY_TABLE_NAME = "entry";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    public static class ColumnEntries implements BaseColumns{
        public static final String ID = _ID;
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String URL = "url";
        public static final String URL_MINIATURE = "thumb_url";
        public static final String DATE = "date";
    }

    public static final String CREATE_ENTRY =
            "CREATE TABLE " + ENTRY_TABLE_NAME + "(" +
                    ColumnEntries.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnEntries.TITLE + " " + STRING_TYPE + " not null," +
                    ColumnEntries.DESCRIPTION + " " + STRING_TYPE + "," +
                    ColumnEntries.URL + " " + STRING_TYPE + "," +
                    ColumnEntries.URL_MINIATURE + " " + STRING_TYPE + ","+
                    ColumnEntries.DATE + " " + STRING_TYPE + ")";

}
