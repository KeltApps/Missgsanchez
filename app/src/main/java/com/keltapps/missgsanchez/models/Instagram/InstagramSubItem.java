package com.keltapps.missgsanchez.models.Instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class InstagramSubItem {
    @SerializedName("location")
    InstagramLocation location;
    @SerializedName("images")
    InstagramResolutionImages instagramImages;
    @SerializedName("videos")
    InstagramResolutionVideos instagramVideos;
    @SerializedName("likes")
    InstagramLikes instagramLikes;
    @SerializedName("id")
    String id;
    @SerializedName("user")
    InstagramUser instagramUser;
    @SerializedName("caption")
    InstagramCaption instagramCaption;
    @SerializedName("created_time")
    int time;

    public InstagramLocation getLocation() {
        return location;
    }

    public void setLocation(InstagramLocation location) {
        this.location = location;
    }


    public InstagramResolutionImages getInstagramImages() {
        return instagramImages;
    }

    public void setInstagramImages(InstagramResolutionImages instagramImages) {
        this.instagramImages = instagramImages;
    }

    public InstagramResolutionVideos getInstagramVideos() {
        return instagramVideos;
    }

    public void setInstagramVideos(InstagramResolutionVideos instagramVideos) {
        this.instagramVideos = instagramVideos;
    }

    public InstagramLikes getInstagramLikes() {
        return instagramLikes;
    }

    public void setInstagramLikes(InstagramLikes instagramLikes) {
        this.instagramLikes = instagramLikes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InstagramUser getInstagramUser() {
        return instagramUser;
    }

    public void setInstagramUser(InstagramUser instagramUser) {
        this.instagramUser = instagramUser;
    }

    public InstagramCaption getInstagramCaption() {
        return instagramCaption;
    }

    public void setInstagramCaption(InstagramCaption instagramCaption) {
        this.instagramCaption = instagramCaption;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
