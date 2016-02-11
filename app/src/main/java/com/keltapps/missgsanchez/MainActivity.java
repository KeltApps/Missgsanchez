package com.keltapps.missgsanchez;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.keltapps.missgsanchez.fragments.PostFragment;
import com.keltapps.missgsanchez.fragments.SplashFragment;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.utils.FeedDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SplashFragment splashFragment = new SplashFragment();
            fragmentTransaction.add(R.id.fragment_container, splashFragment, getString(R.string.fragment_splashFragment));
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
                        if (savedInstanceState == null)
                            changeToPostFragment(oldPost, true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle error
                        Log.v("prueba", "Error volley: " + volleyError);
                        if (savedInstanceState == null)
                            changeToPostFragment(true, false);
                    }
                }
        ));
    }

    private void changeToPostFragment(boolean oldPost, boolean connection) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PostFragment postFragment = new PostFragment();
        Bundle args = new Bundle();
        args.putBoolean(PostFragment.TAG_ARGS_OLD_POST, oldPost);
        args.putBoolean(PostFragment.TAG_ARGS_CONNECTION, connection);
        postFragment.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container, postFragment, getString(R.string.fragment_postFragment));
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
