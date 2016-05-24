package com.keltapps.missgsanchez.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.InstagramAPI;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.FeedDatabase;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.adapters.InstagramCursorAdapter;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class InstagramTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, Response.Listener<String>, Response.ErrorListener {
    private static final String TAG = InstagramTabFragment.class.getSimpleName();
    private static final String TAG_LIMIT_QUERY = "limit_query";
    private int mLastFirstVisibleItem = 0;
    private StickyListHeadersListView stickyList;
    private InstagramCursorAdapter instagramCursorAdapter = null;
    private boolean loading = true;
    private static boolean noMorePages = false;
    private String maxId = "";
    private int totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instagram, container, false);
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                InstagramAPI.getMediaUrlGet(null),
                InstagramTabFragment.this, InstagramTabFragment.this));
        stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.fragment_instagram_list);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        instagramCursorAdapter = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        noMorePages = true;
        sqlQuery(true, 0);
    }

    @Override
    public void onResponse(String response) {
        int returnSynchronize = FeedDatabase.getInstance(getActivity()).synchronizeInstagram(response);
        if (returnSynchronize == FeedDatabase.RETURN_EMPTY_PAGE) {
            noMorePages = true;
            sqlQuery(true, 0);
            return;
        }
        boolean oldPost = returnSynchronize == FeedDatabase.RETURN_OLD_POST;
        sqlQuery(oldPost, totalItemCount);

    }

    /**
     * Create sql query
     *
     * @param oldPost        if it is true, totalItemCount is ignored
     * @param totalItemCount Actual number of items in the adapter
     */
    private void sqlQuery(boolean oldPost, int totalItemCount) {
        Bundle bundle = new Bundle();
        if (oldPost) {
            bundle.putInt(TAG_LIMIT_QUERY, 0);
            getLoaderManager().initLoader(0, bundle, this);
        } else {
            bundle.putInt(TAG_LIMIT_QUERY, totalItemCount + InstagramAPI.getMediaPostPerPage());
            getLoaderManager().initLoader(totalItemCount + InstagramAPI.getMediaPostPerPage(), bundle, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 0)
            return new CursorLoader(getActivity(), EntryProvider.CONTENT_URI_INSTAGRAM, null, null, null,
                    ScriptDatabase.ColumnInstagram.TIME + " DESC");
        else
            return new CursorLoader(getActivity(), Uri.parse(EntryProvider.CONTENT_URI_INSTAGRAM_LIMIT + "/" + args.get(TAG_LIMIT_QUERY)),
                    null, null, null, ScriptDatabase.ColumnInstagram.TIME + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int position = data.getPosition();
        data.moveToPosition(data.getCount() - 1);
        maxId = data.getString(data.getColumnIndex(ScriptDatabase.ColumnInstagram.ID_POST));
        data.moveToPosition(position);
        if (instagramCursorAdapter == null) {
            instagramCursorAdapter = new InstagramCursorAdapter(getActivity(), data);
            stickyList.setAdapter(instagramCursorAdapter);
            stickyList.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalCount) {
                    if (noMorePages)
                        return;
                    totalItemCount = totalCount;
                    if (firstVisibleItem > mLastFirstVisibleItem) //check for scroll down
                    {
                        if (loading) {
                            if ((visibleItemCount + firstVisibleItem) >= totalCount - 2) {
                                loading = false;
                                if (!maxId.equals(""))
                                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                                            InstagramAPI.getMediaUrlGet(maxId),
                                            InstagramTabFragment.this, InstagramTabFragment.this));
                            }
                        }
                    }
                    mLastFirstVisibleItem = firstVisibleItem;
                }
            });
        }
        if (!noMorePages)
            loading = true;
        instagramCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (instagramCursorAdapter != null)
            instagramCursorAdapter.changeCursor(null);
    }
}