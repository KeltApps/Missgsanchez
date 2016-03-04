package com.keltapps.missgsanchez.fragments;

import android.animation.ValueAnimator;
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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.FeedDatabase;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.adapters.PostAdapter;

import java.util.List;


public class BlogPageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = PostAdapter.class.getSimpleName();
    private static final String TAG_LIMIT_QUERY = "limit_query";
    public static final String TAG_ARGS_OLD_POST = "old_post";
    public static final String TAG_ARGS_CONNECTION = "connection";

    PostAdapter postAdapter;
    boolean loading = true;
    public static boolean noMorePages = false;
    private DrawerLayout drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + TAG);
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        animDrawer(rootView);


        Bundle args = getArguments();
        sqlQuery(args.getBoolean(TAG_ARGS_OLD_POST), 0);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_post_recyclerView);
        postAdapter = new PostAdapter(getActivity(), null);
        recyclerView.setAdapter(postAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getActivity().getSupportFragmentManager();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (args.getBoolean(TAG_ARGS_CONNECTION))
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (noMorePages)
                        return;
                    if (dy > 0) //check for scroll down
                    {
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        final int totalItemCount = linearLayoutManager.getItemCount();
                        int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                        if (loading) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 2) {
                                loading = false;
                                int numberPage = (int) Math.ceil((totalItemCount - 1) * 1.0 / VolleySingleton.POST_PER_PAGE) + 1;
                                VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET, VolleySingleton.URL_GET_POSTS + numberPage,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String data) {

                                                int returnSynchronize = FeedDatabase.getInstance(getActivity()).synchronizeEntries(data);
                                                if (returnSynchronize == FeedDatabase.RETURN_EMPTY_PAGE) {
                                                    noMorePages = true;
                                                    sqlQuery(true, 0);
                                                    return;
                                                }
                                                boolean oldPost = returnSynchronize == FeedDatabase.RETURN_OLD_POST;
                                                sqlQuery(oldPost, totalItemCount);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                // Handle error
                                                sqlQuery(true, 0);
                                            }
                                        }
                                ));
                            }
                        }
                    }
                }
            });
        return rootView;
    }

    private void animDrawer(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.fragment_post_toolbar);
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.activity_main_drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toggle.syncState();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (pref.getBoolean(BlogInsidePostFragment.TAG_ANIM, false)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(BlogInsidePostFragment.TAG_ANIM, false);
            editor.commit();
            ValueAnimator anim = ValueAnimator.ofFloat(1, 0);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float slideOffset = (Float) valueAnimator.getAnimatedValue();
                    toggle.onDrawerSlide(drawer, slideOffset);
                }
            });
            anim.setInterpolator(new DecelerateInterpolator());
            // You can change this duration to more closely match that of the default animation.
            anim.setDuration(500);
            anim.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        postAdapter = null;
    }

    private void sqlQuery(boolean oldPost, int totalItemCount) {
        Bundle bundle = new Bundle();
        if (oldPost) {
            bundle.putInt(TAG_LIMIT_QUERY, 0);
            getLoaderManager().initLoader(0, bundle, this);
        } else {
            bundle.putInt(TAG_LIMIT_QUERY, totalItemCount + VolleySingleton.POST_PER_PAGE);
            getLoaderManager().initLoader(totalItemCount + VolleySingleton.POST_PER_PAGE, bundle, this);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 0)
            return new CursorLoader(getActivity(), EntryProvider.CONTENT_URI, null, null, null, ScriptDatabase.ColumnEntries.DATE + " DESC");
        else
            return new CursorLoader(getActivity(), Uri.parse(EntryProvider.CONTENT_URI_LIMIT + "/" + args.get(TAG_LIMIT_QUERY)), null, null, null, ScriptDatabase.ColumnEntries.DATE + " DESC");


    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (!noMorePages) {   //Add the "load more" element
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{ScriptDatabase.ColumnEntries.ID});
            matrixCursor.addRow(new String[]{"-1"});
            Cursor[] cursors = {data, matrixCursor};
            Cursor extendedCursor = new MergeCursor(cursors);
            postAdapter.swapCursor(extendedCursor);
            loading = true;
        } else
            postAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        if (postAdapter != null)
            postAdapter.changeCursor(null);
    }

    public interface OnPostExpandListener {
        void onClickPostListener(List<String> arrayListPhotos, int actualPositionViewPager, String timeAgo, String title, String content, String urlPost);
    }


}
