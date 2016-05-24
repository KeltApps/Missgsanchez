package com.keltapps.missgsanchez.utils;

import android.provider.BaseColumns;


public class ScriptDatabase {

    public static final String BLOG_TABLE_NAME = "blog";
    public static final String PHOTOS_BLOG_TABLE_NAME = "photosBlog";
    public static final String INSTAGRAM_TABLE_NAME = "instagram";
    public static final String YOUTUBE_TABLE_NAME = "youTube";
    private static final String STRING_TYPE = "TEXT";
    private static final String INT_TYPE = "INTEGER";

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
        public static final String URL_VIDEO = "url_video";
        public static final String LIKES = "likes";
        public static final String TITLE = "title";
    }

    public static final String CREATE_INSTAGRAM =
            "CREATE TABLE " + INSTAGRAM_TABLE_NAME + "(" +
                    ColumnInstagram.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnInstagram.ID_POST + " " + STRING_TYPE + "," +
                    ColumnInstagram.URL_PROFILE_PHOTO + " " + STRING_TYPE + "," +
                    ColumnInstagram.USER + " " + STRING_TYPE + "," +
                    ColumnInstagram.LOCATION + " " + STRING_TYPE + "," +
                    ColumnInstagram.TIME + " " + INT_TYPE + "," +
                    ColumnInstagram.URL_PHOTO + " " + STRING_TYPE + "," +
                    ColumnInstagram.URL_VIDEO + " " + STRING_TYPE + "," +
                    ColumnInstagram.LIKES + " " + INT_TYPE + "," +
                    ColumnInstagram.TITLE + " " + STRING_TYPE + ")";

    public static class ColumnYouTube implements BaseColumns {
        public static final String ID = _ID;
        public static final String ID_YOUTUBE = "id_youtube";
        public static final String PUBLISHED_AT = "publishedAt";
        public static final String TITLE_YOUTUBE = "title";
        public static final String THUMBNAILS = "thumbnails";
        public static final String DESCRIPTION = "description";
        public static final String DURATION = "duration";
        public static final String VIEW_COUNT = "viewCount";
        public static final String LIKE_COUNT = "likeCount";
    }

    public static final String CREATE_YOUTUBE =
            "CREATE TABLE " + YOUTUBE_TABLE_NAME + "(" +
                    ColumnYouTube.ID + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnYouTube.ID_YOUTUBE + " " + STRING_TYPE + "," +
                    ColumnYouTube.PUBLISHED_AT + " " + STRING_TYPE + "," +
                    ColumnYouTube.TITLE_YOUTUBE + " " + STRING_TYPE + "," +
                    ColumnYouTube.THUMBNAILS + " " + STRING_TYPE + "," +
                    ColumnYouTube.DESCRIPTION + " " + STRING_TYPE + "," +
                    ColumnYouTube.DURATION + " " + STRING_TYPE + "," +
                    ColumnYouTube.VIEW_COUNT + " " + INT_TYPE + "," +
                    ColumnYouTube.LIKE_COUNT + " " + INT_TYPE + ")";

}
