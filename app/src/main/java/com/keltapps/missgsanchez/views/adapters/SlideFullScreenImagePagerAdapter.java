package com.keltapps.missgsanchez.views.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.keltapps.missgsanchez.fragments.SlideFullScreenImagePageFragment;

import java.util.List;

/**
 * Created by sergio on 2/03/16 for KelpApps.
 */
public class SlideFullScreenImagePagerAdapter extends FragmentStatePagerAdapter {
    List<String> arrayPhotos;

    public SlideFullScreenImagePagerAdapter(FragmentManager fm, List<String> arrayPhotos) {
        super(fm);
        this.arrayPhotos = arrayPhotos;
    }

    @Override
    public Fragment getItem(int position) {
        SlideFullScreenImagePageFragment slideFullScreenImagePageFragment = new SlideFullScreenImagePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SlideFullScreenImagePageFragment.TAG_ARGS_URL_PHOTO, arrayPhotos.get(position));
        slideFullScreenImagePageFragment.setArguments(bundle);
        return slideFullScreenImagePageFragment;
    }


    @Override
    public int getCount() {
        return arrayPhotos.size();
    }


}