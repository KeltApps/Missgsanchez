package com.keltapps.missgsanchez.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.network.VolleySingleton;
import com.keltapps.missgsanchez.views.TouchImageView;

/**
 * Created by sergio on 21/02/16 for KelpApps.
 */
public class SlideFullScreenImagePageFragment extends Fragment {
    private static final String TAG = SlideFullScreenImagePageFragment.class.getSimpleName();
    public static String TAG_ARGS_URL_PHOTO = "args_url_photo";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_full_screen_image_page, container, false);


        final View imageView = rootView.findViewById(R.id.fragment_slide_full_screen_image_view);


        Log.d(TAG, "onCreateView: ");

        final View decorView = getActivity().getWindow().getDecorView();

        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.activity_full_screen_toolbar);
        final int statusBarHeight = (int) toolbar.getY();
        final GestureDetector clickDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: ");
                boolean visible = (decorView.getSystemUiVisibility() & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
                if (visible)
                    hideSystemUI(decorView, toolbar);
                else
                    showSystemUI(decorView, toolbar, statusBarHeight);
                //visible = !visible;
                return false;
            }
        });
        TouchImageView touchImageView = (TouchImageView) rootView.findViewById(R.id.fragment_slide_full_screen_image_view);
        touchImageView.setClickDetector(clickDetector);

        String urlMiniature, urlMiniatureFullResolution, urlMiniatureName;
        urlMiniature = urlMiniatureFullResolution = urlMiniatureName = getArguments().getString(TAG_ARGS_URL_PHOTO);
        if (urlMiniature.contains("localhost"))
            urlMiniature = urlMiniature.replace("localhost", VolleySingleton.WORDPRESS);
        if (urlMiniature.contains("/"))
            urlMiniatureName = urlMiniature.substring(urlMiniature.lastIndexOf("/"));
        if (urlMiniatureName.contains("-"))
            urlMiniatureFullResolution = urlMiniature.substring(0, urlMiniature.lastIndexOf("/")) + urlMiniatureName.substring(0, urlMiniatureName.lastIndexOf("-")) + urlMiniature.substring(urlMiniature.lastIndexOf("."));

        LoadImages.setImageCenterInside(getActivity(), urlMiniatureFullResolution, urlMiniature, (TouchImageView) imageView);
        return rootView;
    }

    private void hideSystemUI(final View mDecorView, Toolbar toolbar) {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        animateHeight(toolbar, (int) toolbar.getY(), -1 * toolbar.getHeight(), 500);
    }

    private void showSystemUI(View mDecorView, Toolbar toolbar, int statusBarHeight) {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        animateHeight(toolbar, (int) toolbar.getY(), statusBarHeight, 500);
    }

    public void animateHeight(final View view, int from, int to, int duration) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setY(val);
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

}
