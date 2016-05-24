package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeSearchSubItem {
    @SerializedName("id")
    YouTubeID id;

    public YouTubeID getId() {
        return id;
    }

    public void setId(YouTubeID id) {
        this.id = id;
    }
}
