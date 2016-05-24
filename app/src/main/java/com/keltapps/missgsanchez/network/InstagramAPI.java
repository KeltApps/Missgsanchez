package com.keltapps.missgsanchez.network;

/**
 * Created by sergio on 17/04/16 for KelpApps.
 */
public class InstagramAPI {
    private static String MEDIA_BASE_URL = "https://www.instagram.com/missgsanchez/media/";
    private static int MEDIA_POST_PER_PAGE = 20;

    public static String getMediaUrlGet(String mediaMaxID) {
        if (mediaMaxID == null || mediaMaxID.equals(""))
            return MEDIA_BASE_URL;
        return MEDIA_BASE_URL + "?max_id=" + mediaMaxID;
    }

    public static int getMediaPostPerPage() {
        return MEDIA_POST_PER_PAGE;
    }

}
