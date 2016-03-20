package com.keltapps.missgsanchez.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keltapps.missgsanchez.R;


public class SplashFragment extends Fragment {
    private static final String TAG = SplashFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + TAG);
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(BlogInsidePostFragment.TAG_ANIM, false);
        editor.apply();
        return rootView;
    }

}
