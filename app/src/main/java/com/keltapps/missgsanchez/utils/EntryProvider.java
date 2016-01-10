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
    private static final String prefix_uri = "content://com.keltapps.missgsanchez.contentproviders/";
    private static final String uri = prefix_uri + ScriptDatabase.ENTRY_TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(uri);
    private static final String MIME_UNIQUE = "vnd.android.cursor.dir/vnd.missgsanchez.entry";
    private static final String MIME_LIST = "vnd.android.cursor.item/vnd.missgsanchez.entry";

    private FeedDatabase feedDatabase;


    //UriMatcher
    private static final int ENTRIES = 1;
    private static final int ENTRIES_ID = 2;
    private static final UriMatcher uriMatcher;

    //Initialize UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(prefix_uri, ScriptDatabase.ENTRY_TABLE_NAME, ENTRIES);
        uriMatcher.addURI(prefix_uri, ScriptDatabase.ENTRY_TABLE_NAME + "/#", ENTRIES_ID);
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
        if (uriMatcher.match(uri) == ENTRIES_ID)
            where = "_id=" + uri.getLastPathSegment();
        return feedDatabase.getWritableDatabase().query(ScriptDatabase.ENTRY_TABLE_NAME, projection, where,
                selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ENTRIES:
                return MIME_UNIQUE;
            case ENTRIES_ID:
                return MIME_LIST;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = feedDatabase.getWritableDatabase().insert(ScriptDatabase.ENTRY_TABLE_NAME, null, values);
        return ContentUris.withAppendedId(CONTENT_URI, regId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where = selection;
        if (uriMatcher.match(uri) == ENTRIES_ID)
            where = "_id=" + uri.getLastPathSegment();
        return feedDatabase.getWritableDatabase().delete(ScriptDatabase.ENTRY_TABLE_NAME, where, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String where = selection;
        if (uriMatcher.match(uri) == ENTRIES_ID)
            where = "_id=" + uri.getLastPathSegment();
        return feedDatabase.getWritableDatabase().update(ScriptDatabase.ENTRY_TABLE_NAME, values, where, selectionArgs);
    }


}
