package com.keltapps.missgsanchez.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.FeedDatabase;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class InstagramTabFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instagram, container, false);

        StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.fragment_instagram_list);
    //    Insta adapter = new MyAdapter(this);
     //   stickyList.setAdapter(adapter);

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(new StringRequest(Request.Method.GET,
                VolleySingleton.URL_GET_INSTAGRAM ,
                InstagramTabFragment.this, InstagramTabFragment.this));

        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

      FeedDatabase.getInstance(getActivity()).synchronizeInstagram(response);

    }
}