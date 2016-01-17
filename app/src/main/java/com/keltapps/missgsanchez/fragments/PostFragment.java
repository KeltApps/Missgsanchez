package com.keltapps.missgsanchez.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
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
import com.keltapps.missgsanchez.MainActivity;
import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.FeedDatabase;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.adapters.PostAdapter;

import org.jsoup.Jsoup;


public class PostFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = PostAdapter.class.getSimpleName();
    private static final String TAG_LIMIT_QUERY = "limit_query";
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    boolean loading = true;
    int loader_id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        Bundle args = getArguments();
        sqlQuery(args.getBoolean(MainActivity.TAG_ARGS_OLD_POST),0);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.post_fragment_recyclerView);
        postAdapter = new PostAdapter(getActivity(), null);
        recyclerView.setAdapter(postAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (args.getBoolean(MainActivity.TAG_ARGS_CONNECTION))
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) //check for scroll down
                    {
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        final int totalItemCount = linearLayoutManager.getItemCount();
                        int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                        if (loading) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 2) {
                                loading = false;
                                int numberPage = (int) Math.floor(totalItemCount / FeedDatabase.POST_PER_PAGE) + 1;
                                if(FeedDatabase.nextPage != null)
                                VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET, MainActivity.URL_HTTP + "/page/" + numberPage,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String data) {
                                                sqlQuery(FeedDatabase.getInstance(getActivity()).
                                                        synchronizeEntries(Jsoup.parse(data)),totalItemCount);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                // Handle error
                                                Log.d(TAG, "Error Volley: " + volleyError.getMessage());
                                                FeedDatabase.nextPage = null;
                                                sqlQuery(true, 0);
                                            }
                                        }
                                ));
                            }
                        }
                    }
                }
            });

      //  inflater.inflate(R.layout.item_load_more,container);
        return rootView;
    }
/*
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }*/

    private void sqlQuery(boolean oldPost, int totalItemCount) {
        Bundle bundle = new Bundle();
        if (oldPost) {
            bundle.putInt(TAG_LIMIT_QUERY, 0);
            getLoaderManager().initLoader(loader_id, bundle, this);
        } else {
            bundle.putInt(TAG_LIMIT_QUERY, totalItemCount + FeedDatabase.POST_PER_PAGE);
            getLoaderManager().initLoader(loader_id, bundle, this);
        }
        loader_id++;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ((int) args.get(TAG_LIMIT_QUERY) == 0)
            return new CursorLoader(getActivity(), EntryProvider.CONTENT_URI, null, null, null, ScriptDatabase.ColumnEntries.DATE_FORMATTED + " DESC");
        else
            return new CursorLoader(getActivity(), Uri.parse(EntryProvider.CONTENT_URI_LIMIT+"/" + args.get(TAG_LIMIT_QUERY)), null, null, null, ScriptDatabase.ColumnEntries.DATE_FORMATTED + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Add the "load more" element
        if(FeedDatabase.nextPage != null) {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{ScriptDatabase.ColumnEntries.ID});
            matrixCursor.addRow(new String[]{"-1"});
            Cursor[] cursors = {data, matrixCursor};
            Cursor extendedCursor = new MergeCursor(cursors);
            postAdapter.swapCursor(extendedCursor);
            loading = true;
        }else
            postAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        postAdapter.swapCursor(null);
    }

}
