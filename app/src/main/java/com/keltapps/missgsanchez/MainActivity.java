package com.keltapps.missgsanchez;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.keltapps.missgsanchez.fragments.BlogInsidePostFragment;
import com.keltapps.missgsanchez.fragments.BlogPageFragment;
import com.keltapps.missgsanchez.fragments.SplashFragment;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.FeedDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BlogPageFragment.OnPostExpandListener, NavigationView.OnNavigationItemSelectedListener, BlogInsidePostFragment.OnImageFullScreenListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static boolean activityDestroyed = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SplashFragment splashFragment = new SplashFragment();
            fragmentTransaction.add(R.id.activity_main_frame_layout, splashFragment, getString(R.string.fragment_splashFragment));
            fragmentTransaction.commit();
        }

        VolleySingleton.getInstance(this).addToRequestQueue(new StringRequest(Request.Method.GET, VolleySingleton.URL_GET_POSTS + 0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String data) {
                        int returnSynchronize = FeedDatabase.getInstance(MainActivity.this).synchronizeEntries(data);
                        if (returnSynchronize == FeedDatabase.RETURN_EMPTY_PAGE)
                            return;
                        boolean oldPost = returnSynchronize == FeedDatabase.RETURN_OLD_POST;
                        if (savedInstanceState == null || activityDestroyed)
                            changeToPostFragment(oldPost, true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle error
                        Log.v("prueba", "Error volley: " + volleyError);
                        if (savedInstanceState == null || activityDestroyed)
                            changeToPostFragment(true, false);
                    }
                }
        ));
    }

    private void changeToPostFragment(boolean oldPost, boolean connection) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlogPageFragment blogPageFragment = new BlogPageFragment();
        Bundle args = new Bundle();
        args.putBoolean(BlogPageFragment.TAG_ARGS_OLD_POST, oldPost);
        args.putBoolean(BlogPageFragment.TAG_ARGS_CONNECTION, connection);
        blogPageFragment.setArguments(args);
        fragmentTransaction.replace(R.id.activity_main_frame_layout, blogPageFragment, getString(R.string.fragment_blogPageFragment));
        try {
            fragmentTransaction.commitAllowingStateLoss();
            activityDestroyed = false;
        } catch (IllegalStateException e) {
            activityDestroyed = true;
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //       if (id == R.id.action_settings) {
        //       return true;
        //     }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp: ");
        //This method is called when the up button is pressed. Just the pop back stack.
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


}
