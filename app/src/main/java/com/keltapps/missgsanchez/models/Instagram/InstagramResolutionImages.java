package com.keltapps.missgsanchez.models.Instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class InstagramResolutionImages {
    @SerializedName("low_resolution")
    InstagramMedia lowResolution;
    @SerializedName("thumbnail")
    InstagramMedia thumbnail;
    @SerializedName("standard_resolution")
    InstagramMedia standard_resolution;

    public InstagramMedia getLowResolution() {
        return lowResolution;
    }

    public void setLowResolution(InstagramMedia lowResolution) {
        this.lowResolution = lowResolution;
    }

    public InstagramMedia getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(InstagramMedia thumbnail) {
        this.thumbnail = thumbnail;
    }

    public InstagramMedia getStandard_resolution() {
        return standard_resolution;
    }

    public void setStandard_resolution(InstagramMedia standard_resolution) {
        this.standard_resolution = standard_resolution;
    }

}
