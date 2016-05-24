package com.keltapps.missgsanchez.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.widget.VideoView;

import com.keltapps.missgsanchez.Activities.MainActivity;


public class MyVideoView extends VideoView {
    private int stopPosition;
    private MediaPlayer mediaPlayer;
    private boolean isVolumeActivated = false;

    public MyVideoView(Context context) {
        super(context);
        registerReceiver(context);
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mp.setVolume(0, 0);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopPlayback();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (MainActivity.ACTION_INSTAGRAM_NOT_SELECTED.equals(action)) {
                stopPosition = getCurrentPosition();
                pause();
            } else if (MainActivity.ACTION_INSTAGRAM_SELECTED.equals(action)) {
                seekTo(stopPosition);
                start();
            }
        }
    };

    private void registerReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.ACTION_INSTAGRAM_SELECTED);
        filter.addAction(MainActivity.ACTION_INSTAGRAM_NOT_SELECTED);
        context.registerReceiver(broadcastReceiver, filter);
    }

    public void exchangeVolume() {
        if (isVolumeActivated)
            mediaPlayer.setVolume(0, 0);
        else
            mediaPlayer.setVolume(1, 1);
        isVolumeActivated = !isVolumeActivated;
    }
}
