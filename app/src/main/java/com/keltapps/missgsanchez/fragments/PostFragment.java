package com.keltapps.missgsanchez.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.views.BlogItem;
import com.keltapps.missgsanchez.views.adapters.PostAdapter;

import java.util.ArrayList;
import java.util.Calendar;


public class PostFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);


        ArrayList<BlogItem> listBlogItem = new ArrayList<>();

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



        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.post_fragment_recyclerView);
        PostAdapter postAdapter = new PostAdapter(getContext(),listBlogItem);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return rootView;
    }


}
