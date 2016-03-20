package com.keltapps.missgsanchez;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.keltapps.missgsanchez.views.adapters.SlideFullScreenImagePagerAdapter;

import java.util.List;


public class SlideFullScreenImageActivity extends AppCompatActivity {
    private static final String TAG = SlideFullScreenImageActivity.class.getSimpleName();
    public static final String TAG_ARGS_ARRAY_LIST_PHOTOS = "args_array_list_photos";
    public static final String TAG_ARGS_ACTUAL_POSITION = "args_actual_position";
    public static final String TAG_ARGS_STATUS_BAR_HEIGHT = "args_status_bar_height";
    private String urlImage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_full_screen_image);
        Intent intent = getIntent();
        final List<String> listPhotos = intent.getStringArrayListExtra(TAG_ARGS_ARRAY_LIST_PHOTOS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        int statusBarHeight = intent.getIntExtra(TAG_ARGS_STATUS_BAR_HEIGHT, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_full_screen_toolbar);
        toolbar.setY(statusBarHeight);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        int actualPosition = intent.getIntExtra(TAG_ARGS_ACTUAL_POSITION, 1);
        final int listPhotosSize = listPhotos.size();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.photosSlideCount,actualPosition + 1,listPhotosSize));
        }

        ViewPager mPager = (ViewPager) findViewById(R.id.activity_full_screen_view_pager);
        PagerAdapter mPagerAdapter = new SlideFullScreenImagePagerAdapter(getSupportFragmentManager(), listPhotos);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(actualPosition);
        urlImage = listPhotos.get(actualPosition);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                ActionBar actionBar = getSupportActionBar();
                if(actionBar!=null)
                    actionBar.setTitle(getString(R.string.photosSlideCount,position + 1,listPhotosSize));
                urlImage = listPhotos.get(position);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_slide_full_screen_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent shareIntent = ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setText(urlImage)
                    .getIntent();
            if (shareIntent.resolveActivity(
                    this.getPackageManager()) != null)
                startActivity(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
