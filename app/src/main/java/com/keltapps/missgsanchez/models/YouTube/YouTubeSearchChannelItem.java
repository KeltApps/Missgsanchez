package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

/**
 * Created by sergio on 17/04/16 for KelpApps.
 */
public class YouTubeSearchChannelItem {
    @SerializedName("items")
    private LinkedList<YouTubeSearchChannelSubItem> youTubeSearchChannelSubItems;

    public LinkedList<YouTubeSearchChannelSubItem> getYouTubeSearchChannelSubItems() {
        return youTubeSearchChannelSubItems;
    }

    public void setYouTubeSearchChannelSubItems(LinkedList<YouTubeSearchChannelSubItem> youTubeSearchChannelSubItems) {
        this.youTubeSearchChannelSubItems = youTubeSearchChannelSubItems;
    }
}
