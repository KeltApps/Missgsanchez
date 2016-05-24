package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeVideoItem {
    @SerializedName("items")
    private LinkedList<YouTubeVideoSubItem> youTubeVideoSubItems;

    public LinkedList<YouTubeVideoSubItem> getYouTubeVideoSubItems() {
        return youTubeVideoSubItems;
    }

    public void setYouTubeVideoSubItems(LinkedList<YouTubeVideoSubItem> youTubeVideoSubItems) {
        this.youTubeVideoSubItems = youTubeVideoSubItems;
    }


}
