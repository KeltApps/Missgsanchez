package com.keltapps.missgsanchez.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.network.YouTubeAPI;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.FeedDatabase;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.adapters.YoutubeCursorAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class YouTubeTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, Response.Listener<String>, Response.ErrorListener {
    private static final String TAG = YouTubeTabFragment.class.getSimpleName();
    private static final String TAG_LIMIT_QUERY = "limit_query";
    public static final String TAG_URL_THUMBNAIL_CHANNEL = "urlThumbnailChannel";
    public static boolean noMorePages = false;
    private YoutubeCursorAdapter youtubeCursorAdapter;
    private int totalItemCount;
    private boolean loading = true;
    private String lastPublishedAt = null;
    private int countVideos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_youtube_recyclerView);
        youtubeCursorAdapter = new YoutubeCursorAdapter(getActivity(), (OnYouTubeFullScreenListener) getContext());
        recyclerView.setAdapter(youtubeCursorAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (noMorePages)
                    return;
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 2) {
                            loading = false;
                            youTubeRequestSearch(subtractOneMillisecond(lastPublishedAt));
                        }
                    }
                }
            }
        });

        youTubeRequestSearch(null);
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                YouTubeAPI.getSearchUrlGet(getActivity(), true),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String urlThumbnailChannel = FeedDatabase.getInstance(getActivity()).synchronizeYouTubeSearchChannel(response);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(TAG_URL_THUMBNAIL_CHANNEL, urlThumbnailChannel);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: ");
            }
        }));
        return rootView;
    }

    private String subtractOneMillisecond(String lastPublishedAt) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = df.parse(lastPublishedAt);
            long diff = date.getTime() - 1;
            Date result = new Date(diff);
            return df.format(result);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void youTubeRequestSearch(String publishedBefore) {
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                YouTubeAPI.getSearchUrlGet(getActivity(), publishedBefore),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<String> listString = FeedDatabase.getInstance(getActivity()).synchronizeYouTubeSearch(response);
                        countVideos = listString.size();
                        if (countVideos <= YouTubeAPI.getSearchMaxResults())
                            noMorePages = true;
                        Log.d(TAG, "onResponse: hola");
                        if (listString.size() == 0) {
                            sqlQuery(true, 0);
                        } else {
                            for (String sVideo : listString) {
                                Log.d(TAG, "onResponse: sVideo: " + sVideo);
                                Log.d(TAG, "onResponse: " + YouTubeAPI.getVideoUrlGet(getActivity(), sVideo));
                                VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                                        YouTubeAPI.getVideoUrlGet(getActivity(), sVideo), YouTubeTabFragment.this, YouTubeTabFragment.this));
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: ");
            }
        }));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        noMorePages = true;
        sqlQuery(true, 0);
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG, "onResponse: ");
        lastPublishedAt = FeedDatabase.getInstance(getActivity()).synchronizeYouTubeVideo(response);
        countVideos--;
        if (countVideos == 0)
            sqlQuery(noMorePages, totalItemCount);
    }

    /**
     * Create sql query
     *
     * @param oldPost        if it is true, totalItemCount is ignored
     * @param totalItemCount Actual number of items in the adapter
     */
    private void sqlQuery(boolean oldPost, int totalItemCount) {
        Log.d(TAG, "sqlQuery() called with: " + "oldPost = [" + oldPost + "], totalItemCount = [" + totalItemCount + "]");
        Bundle bundle = new Bundle();
        if (oldPost) {
            bundle.putInt(TAG_LIMIT_QUERY, 0);
            getLoaderManager().initLoader(0, bundle, this);
        } else {
            bundle.putInt(TAG_LIMIT_QUERY, totalItemCount + YouTubeAPI.getSearchMaxResults());
            getLoaderManager().initLoader(totalItemCount + YouTubeAPI.getSearchMaxResults(), bundle, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: ");
        if (id == 0) {
            Log.d(TAG, "onCreateLoader: id = 0");
            return new CursorLoader(getActivity(), EntryProvider.CONTENT_URI_YOUTUBE, null, null, null,
                    ScriptDatabase.ColumnYouTube.PUBLISHED_AT + " DESC");
        } else
            return new CursorLoader(getActivity(), Uri.parse(EntryProvider.CONTENT_URI_YOUTUBE_LIMIT + "/" + args.get(TAG_LIMIT_QUERY)),
                    null, null, null, ScriptDatabase.ColumnYouTube.PUBLISHED_AT + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.d(TAG, "onLoadFinished: " + data.getCount());
        if (!noMorePages) {   //Add the "load more" element
            Log.d(TAG, "onLoadFinished: more");
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{ScriptDatabase.ColumnBlog.ID});
            matrixCursor.addRow(new String[]{"-1"});
            Cursor[] cursors = {data, matrixCursor};
            Cursor extendedCursor = new MergeCursor(cursors);
            youtubeCursorAdapter.swapCursor(extendedCursor);
            loading = true;
        } else
            youtubeCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: ");
        if (youtubeCursorAdapter != null)
            youtubeCursorAdapter.changeCursor(null);
    }

    public interface OnYouTubeFullScreenListener {
        void onClickYouTubeFullScreenListener(String idVideo);
    }
}
