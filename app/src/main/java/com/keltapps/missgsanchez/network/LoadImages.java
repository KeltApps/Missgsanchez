package com.keltapps.missgsanchez.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;



public class LoadImages {

    /**
     * Set the  specified image into imageView. If the network is WIFI or ethernet, the method will load the full resolution image,
     * if not, it will try load the full resolution image from disk. If this image not exist in disk, it will download low resolution image.
     *
     * @param urlFullResolution URL of the image in full resolution
     * @param urlLowResolution  URL of the image in low resolution
     * @param imageView         imageView target
     */
    public static void setImage(final Context context, final String urlFullResolution, final String urlLowResolution, final ImageView imageView) {
        final Picasso picasso = Picasso.with(context);
        // picasso.setIndicatorsEnabled(true);
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if ((networkInfo != null) && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET)) {
            picasso.load(urlFullResolution)
                    .fit()
                    .centerCrop()
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(context)
                                    .load(urlLowResolution)
                                    .fit()
                                    .centerCrop()
                                    .into(imageView);
                        }
                    });

        } else {
            picasso.load(urlFullResolution)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .fit()
                    .centerCrop()
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Picasso.with(context)
                                    .load(urlLowResolution)
                                    .fit()
                                    .centerCrop()
                                    .into(imageView);
                        }
                    });
        }
    }
    public static void setImageCenterInside(final Context context, final String urlFullResolution, final String urlLowResolution, final ImageView imageView) {
        final Picasso picasso = Picasso.with(context);
        // picasso.setIndicatorsEnabled(true);



        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if ((networkInfo != null) && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET)) {
            picasso.load(urlFullResolution)
                    .fit()
                    .centerInside()
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Picasso.with(context)
                                    .load(urlLowResolution)
                                    .fit()
                                    .centerInside()
                                    .into(imageView);
                        }
                    });

        } else {
            picasso.load(urlFullResolution)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .fit()
                    .centerInside()
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Picasso.with(context)
                                    .load(urlLowResolution)
                                    .fit()
                                    .centerInside()
                                    .into(imageView);
                        }
                    });
        }
    }
}
