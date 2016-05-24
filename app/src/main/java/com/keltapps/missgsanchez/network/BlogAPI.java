package com.keltapps.missgsanchez.network;

/**
 * Created by sergio on 17/04/16 for KelpApps.
 */
public class BlogAPI {
    private static final String POST_BASE_URL = "http://192.168.1.65/wp-json/wp/v2/posts";
    private static int POST_PER_PAGE = 10;

    public static String getPostUrlGet(int postPage) {
        return POST_BASE_URL + "?per_page=" + POST_PER_PAGE +"&page=" + postPage;
    }

    public static int getPostPerPage() {
        return POST_PER_PAGE;
    }

    public static void setPostPerPage(int postPerPage) {
        POST_PER_PAGE = postPerPage;
    }
}
