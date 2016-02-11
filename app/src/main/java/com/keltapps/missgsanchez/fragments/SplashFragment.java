package com.keltapps.missgsanchez.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keltapps.missgsanchez.R;

/**
 * Created by sergio on 10/01/16 for KelpApps.
 */
public class SplashFragment extends Fragment {
    private static final String TAG = SplashFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + TAG);
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        return rootView;
    }

}
