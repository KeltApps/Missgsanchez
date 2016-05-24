package com.keltapps.missgsanchez.models.Instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class InstagramLikes {
    @SerializedName("count")
    int count;


    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
