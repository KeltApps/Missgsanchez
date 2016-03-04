package com.keltapps.missgsanchez.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.views.DepthPageTransformer;
import com.keltapps.missgsanchez.views.adapters.SlideScreenImagePagerAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sergio on 11/02/16 for KelpApps.
 */
public class BlogInsidePostFragment extends Fragment {
    private static final String TAG = BlogInsidePostFragment.class.getSimpleName();
    public static String TAG_ARGS_ARRAY_LIST_PHOTOS = "args_array_list_photos";
    public static String TAG_ARGS_ACTUAL_POSITION = "args_actual_position";
    public static String TAG_ARGS_TIME_AGO = "args_time_ago";
    public static String TAG_ARGS_TITLE = "args_title";
    public static String TAG_ARGS_CONTENT = "args_content";
    public static String TAG_ARGS_URL_POST = "args_url_post";
    public static String TAG_ANIM = "anim";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inside_post, container, false);
        Bundle bundle = getArguments();

        animDrawer(rootView);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.inside_post_collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        TextView title = (TextView) rootView.findViewById(R.id.linear_title_textView);
        final String sTitle = bundle.getString(TAG_ARGS_TITLE);
        title.setText(sTitle);
        String content = bundle.getString(TAG_ARGS_CONTENT);
        List<String> listPhotos = bundle.getStringArrayList(TAG_ARGS_ARRAY_LIST_PHOTOS);
        LinearLayout containerContent = (LinearLayout) rootView.findViewById(R.id.fragment_post_inside_container_content);
        setContentPost(content, containerContent, listPhotos);

        ViewPager mPager = (ViewPager) rootView.findViewById(R.id.inside_post_ViewPager);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        PagerAdapter mPagerAdapter = new SlideScreenImagePagerAdapter(getChildFragmentManager(), listPhotos);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(bundle.getInt(TAG_ARGS_ACTUAL_POSITION));
        TextView textTime = (TextView) rootView.findViewById(R.id.extraInformation_textView_time);
        textTime.setText(bundle.getString(TAG_ARGS_TIME_AGO));
        final TextView textPhoto = (TextView) rootView.findViewById(R.id.extraInformation_textView_photo);
        textPhoto.setText((bundle.getInt(TAG_ARGS_ACTUAL_POSITION) + 1) + " / " + listPhotos.size());
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                String text = textPhoto.getText().toString();
                text = text.substring(text.indexOf("/"));
                textPhoto.setText((position + 1) + " " + text);
            }
        });
        final String urlPost = bundle.getString(TAG_ARGS_URL_POST);
        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragment_post_inside_floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = ShareCompat.IntentBuilder
                        .from(getActivity())
                        .setType("text/plain")
                        .setText(sTitle+ "\n" + urlPost)
                        .getIntent();
                if (shareIntent.resolveActivity(
                        getActivity().getPackageManager()) != null)
                    startActivity(shareIntent);
            }
        });

        return rootView;
    }


    private void animDrawer(View rootView) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(TAG_ANIM, true);
        editor.commit();

        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.inside_post_toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.activity_main_drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(drawer, slideOffset);
            }

        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(500);
        anim.start();
    }

    private void setContentPost(String content, LinearLayout containerContent, final List<String> listPhotos) {
        ArrayList<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(content);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();

        int positionPhotos = 0;
        for (int i = 0; i < lines.size(); i++) {
            Document doc = Jsoup.parse(lines.get(i));
            Elements images = doc.select("img");
            if (images.size() == 0) {
                TextView textView = new TextView(getContext());
                textView.setText(Html.fromHtml(lines.get(i)));
                containerContent.addView(textView);
            } else {
                for (Element el : images) {
                    Element img = el.select("img").first();
                    String image = img.absUrl("src");
                    final ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int widthScreen = size.x;
                    widthScreen -= 2 * getResources().getDimension(R.dimen.activity_vertical_margin);

                    int heightImage = Integer.parseInt(img.attr("height"));
                    int widthImage = Integer.parseInt(img.attr("width"));
                    imageView.getLayoutParams().height = widthScreen * heightImage / widthImage;
                    imageView.setTag(positionPhotos);
                    positionPhotos++;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((BlogInsidePostFragment.OnImageFullScreenListener) getActivity()).onClickImageFullScreenListener(listPhotos, (int) v.getTag());
                        }
                    });

                    String urlMiniature, urlFullResolution, urlMiniatureName;
                    urlMiniature = urlFullResolution = urlMiniatureName = image;
                    if (urlMiniature.contains("localhost"))
                        urlMiniature = urlMiniature.replace("localhost", VolleySingleton.WORDPRESS);

                    if (urlMiniature.contains("/"))
                        urlMiniatureName = urlMiniature.substring(urlMiniature.lastIndexOf("/"));
                    if (urlMiniatureName.contains("-"))
                        urlFullResolution = urlMiniature.substring(0, urlMiniature.lastIndexOf("/")) + urlMiniatureName.substring(0, urlMiniatureName.lastIndexOf("-")) + urlMiniature.substring(urlMiniature.lastIndexOf("."));
                    containerContent.addView(imageView);
                    LoadImages.setImage(getContext(), urlFullResolution, urlMiniature, imageView);

                }
            }
        }
    }

    public interface OnImageFullScreenListener {
        void onClickImageFullScreenListener(List<String> arrayListPhotos, int actualPositionViewPager);
    }


}
