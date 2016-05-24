package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeThumbnailsHigh {
    @SerializedName("url")
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
