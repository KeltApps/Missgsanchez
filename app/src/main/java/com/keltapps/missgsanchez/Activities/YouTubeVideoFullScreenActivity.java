package com.keltapps.missgsanchez.Activities;

import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.keltapps.missgsanchez.R;


public class YouTubeVideoFullScreenActivity extends YouTubeBaseActivity {
    public static final String TAG_ARGS_ID_VIDEO = "args_id_video";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);
        final String idVideo = getIntent().getStringExtra(TAG_ARGS_ID_VIDEO);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.activity_video_full_screen_player);
        youTubePlayerView.initialize(getString(R.string.youtube_key), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                player.setShowFullscreenButton(false);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    player.setFullscreen(true);
                player.setShowFullscreenButton(false);
                if (!wasRestored) {
                    player.setFullscreen(true);
                    player.loadVideo(idVideo);
                }
                player.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
            }
        });
    }
}
