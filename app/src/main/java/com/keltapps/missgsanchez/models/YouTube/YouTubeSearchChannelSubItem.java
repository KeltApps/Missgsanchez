package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 17/04/16 for KelpApps.
 */
public class YouTubeSearchChannelSubItem {
    @SerializedName("snippet")
    private YouTubeSnippet youTubeSnippet;

    public YouTubeSnippet getYouTubeSnippet() {
        return youTubeSnippet;
    }

    public void setYouTubeSnippet(YouTubeSnippet youTubeSnippet) {
        this.youTubeSnippet = youTubeSnippet;
    }
}
