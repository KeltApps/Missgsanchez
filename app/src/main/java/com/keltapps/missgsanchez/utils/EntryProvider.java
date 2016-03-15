package com.keltapps.missgsanchez.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by sergio on 9/01/16 for KelpApps.
 */
public class EntryProvider extends ContentProvider {
    private static final String prefix_content = "content://";
    private static final String prefix_uri = "com.keltapps.missgsanchez.contentproviders";
    private static final String uri = prefix_content + prefix_uri + "/" + ScriptDatabase.BLOG_TABLE_NAME;
    private static final String uri_photos = prefix_content + prefix_uri + "/" + ScriptDatabase.PHOTOS_BLOG_TABLE_NAME;
    private static final String suffix_uri_limit = "/limit";
    private static final String uri_limit = prefix_content + prefix_uri + "/" + ScriptDatabase.BLOG_TABLE_NAME + suffix_uri_limit;
    public static final Uri CONTENT_URI = Uri.parse(uri);
    public static final Uri CONTENT_URI_LIMIT = Uri.parse(uri_limit);
    public static final Uri CONTENT_URI_PHOTOS = Uri.parse(uri_photos);
    private static final String MIME_LIST = "vnd.android.cursor.dir/vnd.missgsanchez.entry";
    private static final String MIME_UNIQUE = "vnd.android.cursor.item/vnd.missgsanchez.entry";

    private FeedDatabase feedDatabase;


    //UriMatcher
    private static final int ENTRIES = 1;
    private static final int ENTRIES_ID = 2;
    private static final int ENTRIES_LIMIT = 3;
    private static final int PHOTOS = 4;
    private static final UriMatcher uriMatcher;

    //Initialize UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(prefix_uri, ScriptDatabase.BLOG_TABLE_NAME, ENTRIES);
        uriMatcher.addURI(prefix_uri, ScriptDatabase.BLOG_TABLE_NAME + "/#", ENTRIES_ID);
        uriMatcher.addURI(prefix_uri, ScriptDatabase.BLOG_TABLE_NAME + suffix_uri_limit + "/#", ENTRIES_LIMIT);
        uriMatcher.addURI(prefix_uri, ScriptDatabase.PHOTOS_BLOG_TABLE_NAME, PHOTOS);
    }


    @Override
    public boolean onCreate() {
        feedDatabase = FeedDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String where = selection;
        switch (uriMatcher.match(uri)) {
            case ENTRIES:
                return feedDatabase.getWritableDatabase().query(ScriptDatabase.BLOG_TABLE_NAME, projection, where,
                        selectionArgs, null, null, sortOrder);
            case ENTRIES_ID:
                where = "_id=" + uri.getLastPathSegment();
                return feedDatabase.getWritableDatabase().query(ScriptDatabase.BLOG_TABLE_NAME, projection, where,
                        selectionArgs, null, null, sortOrder);
            case ENTRIES_LIMIT:
                return feedDatabase.getWritableDatabase().query(ScriptDatabase.BLOG_TABLE_NAME, projection, where,
                        selectionArgs, null, null, sortOrder, uri.getLastPathSegment());
            case PHOTOS:
                return feedDatabase.getWritableDatabase().query(ScriptDatabase.PHOTOS_BLOG_TABLE_NAME, projection, where,
                        selectionArgs, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ENTRIES:
                return MIME_LIST;
            case ENTRIES_ID:
                return MIME_UNIQUE;
            case ENTRIES_LIMIT:
                return MIME_LIST;
            case PHOTOS:
                return MIME_LIST;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId;
        switch (uriMatcher.match(uri)) {
            case ENTRIES:
            case ENTRIES_ID:
            case ENTRIES_LIMIT:
                regId = feedDatabase.getWritableDatabase().insert(ScriptDatabase.BLOG_TABLE_NAME, null, values);
                break;
            case PHOTOS:
                regId = feedDatabase.getWritableDatabase().insert(ScriptDatabase.PHOTOS_BLOG_TABLE_NAME, null, values);
                break;
            default:
                return null;
        }
        return ContentUris.withAppendedId(CONTENT_URI, regId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where = selection;
        switch (uriMatcher.match(uri)) {
            case ENTRIES:
                return feedDatabase.getWritableDatabase().delete(ScriptDatabase.BLOG_TABLE_NAME, where, selectionArgs);
            case ENTRIES_ID:
                where = "_id=" + uri.getLastPathSegment();
                return feedDatabase.getWritableDatabase().delete(ScriptDatabase.BLOG_TABLE_NAME, where, selectionArgs);
            case ENTRIES_LIMIT:
                return feedDatabase.getWritableDatabase().delete(ScriptDatabase.BLOG_TABLE_NAME, where, selectionArgs);
            case PHOTOS:
                return feedDatabase.getWritableDatabase().delete(ScriptDatabase.PHOTOS_BLOG_TABLE_NAME, where, selectionArgs);
            default:
                return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String where = selection;
        switch (uriMatcher.match(uri)) {
            case ENTRIES:
                return feedDatabase.getWritableDatabase().update(ScriptDatabase.BLOG_TABLE_NAME, values, where, selectionArgs);
            case ENTRIES_ID:
                where = "_id=" + uri.getLastPathSegment();
                return feedDatabase.getWritableDatabase().update(ScriptDatabase.BLOG_TABLE_NAME, values, where, selectionArgs);
            case ENTRIES_LIMIT:
                return feedDatabase.getWritableDatabase().update(ScriptDatabase.BLOG_TABLE_NAME, values, where, selectionArgs);
            case PHOTOS:
                return feedDatabase.getWritableDatabase().update(ScriptDatabase.PHOTOS_BLOG_TABLE_NAME, values, where, selectionArgs);
            default:
                return 0;
        }
    }


}
