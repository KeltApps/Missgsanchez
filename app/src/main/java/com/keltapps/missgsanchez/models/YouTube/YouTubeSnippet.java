package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeSnippet {
    @SerializedName("publishedAt")
    String publishedAt;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("thumbnails")
    YouTubeThumbnails youTubeThumbnails;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public YouTubeThumbnails getYouTubeThumbnails() {
        return youTubeThumbnails;
    }

    public void setYouTubeThumbnails(YouTubeThumbnails youTubeThumbnails) {
        this.youTubeThumbnails = youTubeThumbnails;
    }
}
