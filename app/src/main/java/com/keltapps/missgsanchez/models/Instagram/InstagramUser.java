package com.keltapps.missgsanchez.models.Instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class InstagramUser {
    @SerializedName("username")
    String userName;
    @SerializedName("profile_picture")
    String profilePicture;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }


    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
