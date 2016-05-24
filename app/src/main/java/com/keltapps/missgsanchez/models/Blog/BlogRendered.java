package com.keltapps.missgsanchez.models.Blog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class BlogRendered {
    @SerializedName("rendered")
    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
