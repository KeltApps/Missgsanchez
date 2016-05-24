package com.keltapps.missgsanchez.Activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.fragments.BlogInsidePostFragment;
import com.keltapps.missgsanchez.fragments.BlogTabFragment;
import com.keltapps.missgsanchez.fragments.SplashFragment;
import com.keltapps.missgsanchez.fragments.YouTubeTabFragment;
import com.keltapps.missgsanchez.views.adapters.MainTabsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BlogTabFragment.OnBlogTabListener, NavigationView.OnNavigationItemSelectedListener, BlogInsidePostFragment.OnImageFullScreenListener,
        YouTubeTabFragment.OnYouTubeFullScreenListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public final static String ACTION_INSTAGRAM_SELECTED = "com.keltapps.missgsanchez.ACTION_INSTAGRAM_SELECTED";
    public final static String ACTION_INSTAGRAM_NOT_SELECTED = "com.keltapps.missgsanchez.ACTION_INSTAGRAM_NOT_SELECTED";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setUpTabs();
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SplashFragment splashFragment = new SplashFragment();
            fragmentTransaction.add(R.id.activity_main_frame_layout, splashFragment, getString(R.string.fragment_splashFragment));
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.d(TAG, "onBackPressed: ");


            super.onBackPressed();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClickPostListener(List<String> arrayListPhotos, int actualPositionViewPager, String timeAgo, String title, String content, String urlPost) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlogInsidePostFragment blogInsidePostFragment = new BlogInsidePostFragment();

        Bundle args = new Bundle();
        args.putStringArrayList(BlogInsidePostFragment.TAG_ARGS_ARRAY_LIST_PHOTOS, (ArrayList<String>) arrayListPhotos);
        args.putInt(BlogInsidePostFragment.TAG_ARGS_ACTUAL_POSITION, actualPositionViewPager);
        args.putString(BlogInsidePostFragment.TAG_ARGS_TIME_AGO, timeAgo);
        args.putString(BlogInsidePostFragment.TAG_ARGS_TITLE, title);
        args.putString(BlogInsidePostFragment.TAG_ARGS_CONTENT, content);
        args.putString(BlogInsidePostFragment.TAG_ARGS_URL_POST, urlPost);
        blogInsidePostFragment.setArguments(args);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = TransitionInflater.from(this).
                    inflateTransition(android.R.transition.fade);
            transition.setDuration(300);
            blogInsidePostFragment.setEnterTransition(transition);
            blogInsidePostFragment.setExitTransition(transition);
            fragmentTransaction = fragmentTransaction.replace(R.id.activity_main_frame_layout, blogInsidePostFragment, getString(R.string.fragment_blogInsidePostFragment))
                    .addToBackStack(getString(R.string.fragment_blogInsidePostFragment));
        } else {
            fragmentTransaction = fragmentTransaction.replace(R.id.activity_main_frame_layout, blogInsidePostFragment, getString(R.string.fragment_blogInsidePostFragment))
                    .addToBackStack(getString(R.string.fragment_blogInsidePostFragment));
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onPostLoadedListener() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SplashFragment splashFragment = (SplashFragment) fragmentManager.findFragmentByTag(getString(R.string.fragment_splashFragment));
        if (splashFragment == null)
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(splashFragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void setUpTabs() {
        TabLayout tabs = (TabLayout) findViewById(R.id.activity_main_tab_Layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_tab_Layout_view_pager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        PagerAdapter pagerAdapter = new MainTabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tabs.setTabTextColors(ContextCompat.getColor(this, R.color.color_tabs_textNormal), ContextCompat.getColor(this, R.color.color_tabs_textSelected));
        TabLayout.Tab tab = tabs.getTabAt(MainTabsAdapter.TAB_BLOG);
        if (tab != null)
            tab.select();
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == MainTabsAdapter.TAB_INSTAGRAM) {
                    Log.d(TAG, "onTabSelected: ");
                    Intent intent = new Intent(ACTION_INSTAGRAM_SELECTED);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == MainTabsAdapter.TAB_INSTAGRAM) {
                    Intent intent = new Intent(ACTION_INSTAGRAM_NOT_SELECTED);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabReselected: ");
                if (tab.getPosition() == MainTabsAdapter.TAB_INSTAGRAM) {
                    Intent intent = new Intent(ACTION_INSTAGRAM_SELECTED);
                    sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public void onClickImageFullScreenListener(List<String> arrayListPhotos, int actualPositionViewPager) {
        Intent intent = new Intent(this, SlideFullScreenImageActivity.class);
        intent.putStringArrayListExtra(SlideFullScreenImageActivity.TAG_ARGS_ARRAY_LIST_PHOTOS, (ArrayList<String>) arrayListPhotos);
        intent.putExtra(SlideFullScreenImageActivity.TAG_ARGS_ACTUAL_POSITION, actualPositionViewPager);

        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        intent.putExtra(SlideFullScreenImageActivity.TAG_ARGS_STATUS_BAR_HEIGHT, statusBarHeight);
        startActivity(intent);
    }


    @Override
    public void onClickYouTubeFullScreenListener(String idVideo) {
        Intent intent = new Intent(this, YouTubeVideoFullScreenActivity.class);
        intent.putExtra(YouTubeVideoFullScreenActivity.TAG_ARGS_ID_VIDEO, idVideo);
        startActivity(intent);
    }
}
