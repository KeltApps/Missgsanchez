package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeContentDetails {
    @SerializedName("duration")
    String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
