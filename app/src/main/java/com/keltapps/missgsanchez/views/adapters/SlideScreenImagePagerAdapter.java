package com.keltapps.missgsanchez.views.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.keltapps.missgsanchez.fragments.SlideScreenImagePageFragment;

import java.util.ArrayList;
import java.util.List;


public class SlideScreenImagePagerAdapter extends FragmentStatePagerAdapter {
    private final List<String> arrayPhotos;

    public SlideScreenImagePagerAdapter(FragmentManager fm, List<String> arrayPhotos) {
        super(fm);
        this.arrayPhotos = arrayPhotos;
    }

    @Override
    public Fragment getItem(int position) {
        SlideScreenImagePageFragment slideScreenImagePageFragment = new SlideScreenImagePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SlideScreenImagePageFragment.TAG_ARGS_URL_PHOTO, arrayPhotos.get(position));
        bundle.putStringArrayList(SlideScreenImagePageFragment.TAG_ARGS_ARRAY_LIST_PHOTOS, (ArrayList<String>) arrayPhotos);
        bundle.putInt(SlideScreenImagePageFragment.TAG_ARGS_ACTUAL_POSITION, position);
        slideScreenImagePageFragment.setArguments(bundle);
        return slideScreenImagePageFragment;
    }


    @Override
    public int getCount() {
        return arrayPhotos.size();
    }


}