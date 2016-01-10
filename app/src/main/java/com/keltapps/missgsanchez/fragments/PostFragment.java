package com.keltapps.missgsanchez.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.utils.EntryProvider;
import com.keltapps.missgsanchez.utils.ScriptDatabase;
import com.keltapps.missgsanchez.views.adapters.PostAdapter;


public class PostFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView recyclerView;
    PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("prueba", "on create view");
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        getLoaderManager().initLoader(1, null, this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.post_fragment_recyclerView);
        // Cursor cursor = FeedDatabase.getInstance(getActivity()).getEntries();
        //Log.v("prueba","count: " + cursor.getCount());


        postAdapter = new PostAdapter(getActivity(), null);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
/*
        BlogItem blogItem = new BlogItem();
        blogItem.setImageLink("http://www.missgsanchez.com/wp-content/uploads/2015/11/missglemien3-680x450.jpg");
        blogItem.setCounterComments(3);
        Calendar c = Calendar.getInstance();
        blogItem.setDatePost(c.getTime());
        blogItem.setTittle("Copenhagen-DAY 2");
        listBlogItem.add(blogItem);

        listBlogItem.add(blogItem);
        listBlogItem.add(blogItem);
        listBlogItem.add(blogItem);
        listBlogItem.add(blogItem);
        listBlogItem.add(blogItem);
        listBlogItem.add(blogItem);

*/

        Log.v("prueba", "on create view final");

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), EntryProvider.CONTENT_URI, null, null, null, ScriptDatabase.ColumnEntries.DATE + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        postAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
