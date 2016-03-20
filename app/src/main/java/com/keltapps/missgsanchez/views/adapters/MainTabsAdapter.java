package com.keltapps.missgsanchez.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.fragments.BlogTabFragment;
import com.keltapps.missgsanchez.fragments.InstagramTabFragment;
import com.keltapps.missgsanchez.fragments.YouTubeTabFragment;


public class MainTabsAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_TABS = 3;
    public static final int TAB_YOUTUBE = 0;
    public static final int TAB_BLOG = 1;
    public static final int TAB_INSTAGRAM = 2;
    private final Context context;

    public MainTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_YOUTUBE:
                return new YouTubeTabFragment();
            case TAB_BLOG:
                return new BlogTabFragment();
            case TAB_INSTAGRAM:
                return new InstagramTabFragment();
            default:
                return new BlogTabFragment();
        }
    }

    @Override
    public int getCount() {
        return NUMBER_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TAB_YOUTUBE:
                return context.getResources().getString(R.string.tab_youtube);
            case TAB_BLOG:
                return context.getResources().getString(R.string.tab_blog);
            case TAB_INSTAGRAM:
                return context.getResources().getString(R.string.tab_instagram);
            default:
                return null;
        }
    }
}
