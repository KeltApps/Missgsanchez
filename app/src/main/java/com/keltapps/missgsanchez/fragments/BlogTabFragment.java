package com.keltapps.missgsanchez.fragments;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
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
import com.keltapps.missgsanchez.network.BlogAPI;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.FeedDatabase;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.adapters.BlogCursorAdapter;

import java.util.List;


public class BlogTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, Response.Listener<String>, Response.ErrorListener {
    private static final String TAG = BlogCursorAdapter.class.getSimpleName();
    private static final String TAG_LIMIT_QUERY = "limit_query";
    private BlogCursorAdapter blogCursorAdapter;
    private boolean loading = true;
    public static boolean noMorePages = false;
    private int totalItemCount;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: hooooolaaaa");
        View rootView = inflater.inflate(R.layout.fragment_blog, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_post_recyclerView);
        blogCursorAdapter = new BlogCursorAdapter(getActivity());
        recyclerView.setAdapter(blogCursorAdapter);
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
                            int numberPage = (int) Math.ceil((totalItemCount - 1) * 1.0 / BlogAPI.getPostPerPage()) + 1;
                            VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                                    BlogAPI.getPostUrlGet(numberPage),
                                    BlogTabFragment.this, BlogTabFragment.this));
                        }
                    }
                }
            }
        });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                BlogAPI.getPostUrlGet(1),
                BlogTabFragment.this, BlogTabFragment.this));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        blogCursorAdapter = null;
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
            bundle.putInt(TAG_LIMIT_QUERY, totalItemCount + BlogAPI.getPostPerPage());
            getLoaderManager().initLoader(totalItemCount + BlogAPI.getPostPerPage(), bundle, this);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 0)
            return new CursorLoader(getActivity(), EntryProvider.CONTENT_URI, null, null, null,
                    ScriptDatabase.ColumnBlog.DATE + " DESC");
        else
            return new CursorLoader(getActivity(), Uri.parse(EntryProvider.CONTENT_URI_LIMIT + "/" + args.get(TAG_LIMIT_QUERY)),
                    null, null, null, ScriptDatabase.ColumnBlog.DATE + " DESC");
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (!noMorePages) {   //Add the "load more" element
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{ScriptDatabase.ColumnBlog.ID});
            matrixCursor.addRow(new String[]{"-1"});
            Cursor[] cursors = {data, matrixCursor};
            Cursor extendedCursor = new MergeCursor(cursors);
            blogCursorAdapter.swapCursor(extendedCursor);
            loading = true;
        } else
            blogCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        if (blogCursorAdapter != null)
            blogCursorAdapter.changeCursor(null);
    }

    @Override
    public void onResponse(String response) {
        ((OnBlogTabListener) getActivity()).onPostLoadedListener();
        int returnSynchronize = FeedDatabase.getInstance(getActivity()).synchronizeEntries(response);
        if (returnSynchronize == FeedDatabase.RETURN_EMPTY_PAGE) {
            noMorePages = true;
            sqlQuery(true, 0);
            return;
        }
        boolean oldPost = returnSynchronize == FeedDatabase.RETURN_OLD_POST;
        sqlQuery(oldPost, totalItemCount);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        noMorePages = true;
        sqlQuery(true, 0);
        ((OnBlogTabListener) getActivity()).onPostLoadedListener();
    }

    public interface OnBlogTabListener {
        void onClickPostListener(List<String> arrayListPhotos, int actualPositionViewPager, String timeAgo, String title, String content, String urlPost);

        void onPostLoadedListener();
    }
}
