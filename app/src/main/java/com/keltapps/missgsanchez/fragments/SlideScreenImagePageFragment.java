package com.keltapps.missgsanchez.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.keltapps.missgsanchez.R;
import com.keltapps.missgsanchez.network.LoadImages;
import com.keltapps.missgsanchez.network.VolleySingleton;

import java.util.List;

/**
 * Created by sergio on 12/02/16 for KelpApps.
 */
public class SlideScreenImagePageFragment extends Fragment {
    private static final String TAG = SlideScreenImagePageFragment.class.getSimpleName();
    public static String TAG_ARGS_URL_PHOTO = "args_url_photo";
    public static String TAG_ARGS_ARRAY_LIST_PHOTOS = "args_array_list_photos";
    public static String TAG_ARGS_ACTUAL_POSITION = "args_actual_position";


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_screen_image_page, container, false);
        Bundle bundle = getArguments();


        final View imageView = rootView.findViewById(R.id.screen_slide_image_view);

        final List<String> listPhotos = bundle.getStringArrayList(TAG_ARGS_ARRAY_LIST_PHOTOS);
        final int positionPhotos = bundle.getInt(TAG_ARGS_ACTUAL_POSITION);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BlogInsidePostFragment.OnImageFullScreenListener) getActivity()).onClickImageFullScreenListener(listPhotos, positionPhotos);
            }
        });

        String urlMiniature, urlMiniatureFullResolution, urlMiniatureName;
        urlMiniature = urlMiniatureFullResolution = urlMiniatureName = getArguments().getString(TAG_ARGS_URL_PHOTO);
        if (urlMiniature.contains("localhost"))
            urlMiniature = urlMiniature.replace("localhost", VolleySingleton.WORDPRESS);
        if (urlMiniature.contains("/"))
            urlMiniatureName = urlMiniature.substring(urlMiniature.lastIndexOf("/"));
        if (urlMiniatureName.contains("-"))
            urlMiniatureFullResolution = urlMiniature.substring(0, urlMiniature.lastIndexOf("/")) + urlMiniatureName.substring(0, urlMiniatureName.lastIndexOf("-")) + urlMiniature.substring(urlMiniature.lastIndexOf("."));

        LoadImages.setImage(getActivity(), urlMiniatureFullResolution, urlMiniature, (ImageView) imageView);
        return rootView;
    }


}
