package com.keltapps.missgsanchez.network;


import android.content.Context;

import com.keltapps.missgsanchez.R;

public class YouTubeAPI {
    private static String SEARCH_BASE_URL = "https://www.googleapis.com/youtube/v3/search";
    private static String VIDEO_BASE_URL = "https://www.googleapis.com/youtube/v3/videos";
    private static String keyAPI;
    private static String channelId = "UCbbzqKqcsKGQX6zV7jicZww";
    private static String SEARCH_PART = "snippet,id";
    private static String VIDEO_PART = "contentDetails,statistics,snippet";
    private static String SEARCH_ORDER = "date";
    private static int SEARCH_MAX_RESULTS = 20;
    private static String SEARCH_TYPE_VIDEO = "video";
    private static String SEARCH_TYPE_CHANNEL = "channel";
    private static String BASE_URL_VIDEO = "https://www.youtube.com/watch?v=";


    public static String getSearchUrlGet(Context context, String publishedBefore) {
        keyAPI = context.getString(R.string.youtube_key);
        if (publishedBefore == null || publishedBefore.equals(""))
            return SEARCH_BASE_URL + "?key=" + keyAPI + "&channelId=" + channelId + "&part=" +
                    SEARCH_PART + "&order=" + SEARCH_ORDER + "&maxResults=" + SEARCH_MAX_RESULTS + "&type=" + SEARCH_TYPE_VIDEO;
        return SEARCH_BASE_URL + "?key=" + keyAPI + "&channelId=" + channelId + "&part=" +
                SEARCH_PART + "&order=" + SEARCH_ORDER + "&maxResults=" + SEARCH_MAX_RESULTS + "&type=" + SEARCH_TYPE_VIDEO + "&publishedBefore=" + publishedBefore;
    }

    public static String getSearchUrlGet(Context context, boolean searchTypeChannel) {
        keyAPI = context.getString(R.string.youtube_key);
        if(searchTypeChannel)
            return SEARCH_BASE_URL + "?key=" + keyAPI + "&channelId=" + channelId + "&part=" +
                    SEARCH_PART +  "&type=" + SEARCH_TYPE_CHANNEL;
        return SEARCH_BASE_URL + "?key=" + keyAPI + "&channelId=" + channelId + "&part=" +
                SEARCH_PART + "&order=" + SEARCH_ORDER + "&maxResults=" + SEARCH_MAX_RESULTS + "&type=" + SEARCH_TYPE_VIDEO;
    }

    public static String getVideoUrlGet(Context context, String idVideo) {
        keyAPI = context.getString(R.string.youtube_key);
        return VIDEO_BASE_URL + "?key=" + keyAPI + "&part=" + VIDEO_PART + "&id=" + idVideo;
    }

    public static int getSearchMaxResults() {
        return SEARCH_MAX_RESULTS;
    }

    public static String getUrlVideo(String idYoutube){
        return BASE_URL_VIDEO+ idYoutube;
    }

}
