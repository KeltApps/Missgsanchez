package com.keltapps.missgsanchez.models.YouTube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sergio on 13/04/16 for KelpApps.
 */
public class YouTubeVideoSubItem {
    @SerializedName("id")
    String idYouTube;

    @SerializedName("snippet")
    YouTubeSnippet youTubeSnippet;

    @SerializedName("contentDetails")
    YouTubeContentDetails youTubeContentDetails;

    @SerializedName("statistics")
    YouTubeStatistics youTubeStatistics;

    public String getIdYouTube() {
        return idYouTube;
    }

    public void setIdYouTube(String idYouTube) {
        this.idYouTube = idYouTube;
    }

    public YouTubeSnippet getYouTubeSnippet() {
        return youTubeSnippet;
    }

    public void setYouTubeSnippet(YouTubeSnippet youTubeSnippet) {
        this.youTubeSnippet = youTubeSnippet;
    }

    public YouTubeContentDetails getYouTubeContentDetails() {
        return youTubeContentDetails;
    }

    public void setYouTubeContentDetails(YouTubeContentDetails youTubeContentDetails) {
        this.youTubeContentDetails = youTubeContentDetails;
    }

    public YouTubeStatistics getYouTubeStatistics() {
        return youTubeStatistics;
    }

    public void setYouTubeStatistics(YouTubeStatistics youTubeStatistics) {
        this.youTubeStatistics = youTubeStatistics;
    }
}
