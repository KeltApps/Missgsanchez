package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeSearchItem {
    @SerializedName("items")
    private List<YouTubeSearchSubItem> youTubeSearchSubItems;

    public List<YouTubeSearchSubItem> getYouTubeSearchSubItems() {
        return youTubeSearchSubItems;
    }

    public void setYouTubeSearchSubItems(List<YouTubeSearchSubItem> youTubeSearchSubItems) {
        this.youTubeSearchSubItems = youTubeSearchSubItems;
    }
}
