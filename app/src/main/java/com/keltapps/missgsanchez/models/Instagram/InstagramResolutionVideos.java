package com.keltapps.missgsanchez.models.Instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 23/05/16 for KelpApps.
 */
public class InstagramResolutionVideos {
    @SerializedName("standard_resolution")
    InstagramMedia standard_resolution;

    public InstagramMedia getStandard_resolution() {
        return standard_resolution;
    }

    public void setStandard_resolution(InstagramMedia standard_resolution) {
        this.standard_resolution = standard_resolution;
    }
}
