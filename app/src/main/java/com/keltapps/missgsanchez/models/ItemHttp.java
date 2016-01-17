package com.keltapps.missgsanchez.models;

/**
 * Created by sergio on 10/01/16 for KelpApps.
 */
public class ItemHttp {

    private String title;
    private String link;
    private String date;
    private String dateFormatted;
    private int postId;
    private String linkMiniature;
    private String comment;

    public ItemHttp(String title,  String link, String date, String dateFormatted, int postId, String linkMiniature, String comment) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.dateFormatted = dateFormatted;
        this.postId = postId;
        this.linkMiniature = linkMiniature;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getLinkMiniature() {
        return linkMiniature;
    }

    public void setLinkMiniature(String linkMiniature) {
        this.linkMiniature = linkMiniature;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
