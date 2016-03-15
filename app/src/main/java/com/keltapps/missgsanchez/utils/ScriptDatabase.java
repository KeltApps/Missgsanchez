package com.keltapps.missgsanchez.utils;

import android.provider.BaseColumns;

/**
 * Created by sergio on 1/01/16 for KelpApps.
 */
public class ScriptDatabase {

    public static final String BLOG_TABLE_NAME = "blog";
    public static final String PHOTOS_BLOG_TABLE_NAME = "photosBlog";
    public static final String INSTAGRAM_TABLE_NAME = "instagram";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    public static class ColumnBlog implements BaseColumns {
        public static final String ID = _ID;
        public static final String ID_POST = "id_post";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String TEXT = "text";
        public static final String URL = "url";
    }

    public static final String CREATE_BLOG =
            "CREATE TABLE " + BLOG_TABLE_NAME + "(" +
                    ColumnBlog.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnBlog.ID_POST + " " + INT_TYPE + "," +
                    ColumnBlog.TITLE + " " + STRING_TYPE + " not null," +
                    ColumnBlog.DATE + " " + STRING_TYPE + "," +
                    ColumnBlog.TEXT + " " + STRING_TYPE + "," +
                    ColumnBlog.URL + " " + STRING_TYPE + ")";

    public static class ColumnPhotosBlog implements BaseColumns {
        public static final String ID = _ID;
        public static final String ID_POST = "id_post";
        public static final String URL = "url";
    }

    public static final String CREATE_PHOTOS_BLOG =
            "CREATE TABLE " + PHOTOS_BLOG_TABLE_NAME + "(" +
                    ColumnPhotosBlog.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnPhotosBlog.ID_POST + " " + INT_TYPE + "," +
                    ColumnPhotosBlog.URL + " " + STRING_TYPE + ")";

    public static class ColumnInstagram implements BaseColumns {
        public static final String ID = _ID;
        public static final String ID_POST = "id_post";
        public static final String URL_PROFILE_PHOTO = "url_profile_photo";
        public static final String USER = "user";
        public static final String LOCATION = "location";
        public static final String TIME = "time";
        public static final String URL_PHOTO = "url_photo";
        public static final String LIKES = "likes";
        public static final String TITLE = "title";
    }

    public static final String CREATE_INSTAGRAM =
            "CREATE TABLE " + INSTAGRAM_TABLE_NAME + "(" +
                    ColumnInstagram.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnInstagram.ID_POST + " " + INT_TYPE + "," +
                    ColumnInstagram.URL_PROFILE_PHOTO + " " + STRING_TYPE + "," +
                    ColumnInstagram.USER + " " + STRING_TYPE + "," +
                    ColumnInstagram.LOCATION + " " + STRING_TYPE + "," +
                    ColumnInstagram.TIME + " " + INT_TYPE + "," +
                    ColumnInstagram.URL_PHOTO + " " + STRING_TYPE + "," +
                    ColumnInstagram.LIKES + " " + INT_TYPE + "," +
                    ColumnInstagram.TITLE + " " + STRING_TYPE + ")";

}
