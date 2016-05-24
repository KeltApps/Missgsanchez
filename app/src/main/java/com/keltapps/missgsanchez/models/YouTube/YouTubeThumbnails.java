package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeThumbnails {
    @SerializedName("high")
    YouTubeThumbnailsHigh youTubeThumbnailsHigh;

    public YouTubeThumbnailsHigh getYouTubeThumbnailsHigh() {
        return youTubeThumbnailsHigh;
    }

    public void setYouTubeThumbnailsHigh(YouTubeThumbnailsHigh youTubeThumbnailsHigh) {
        this.youTubeThumbnailsHigh = youTubeThumbnailsHigh;
    }
}
