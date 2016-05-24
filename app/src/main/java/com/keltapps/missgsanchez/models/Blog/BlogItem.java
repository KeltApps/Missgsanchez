package com.keltapps.missgsanchez.models.Blog;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BlogItem {
    @SerializedName("id")
    private int idPost;
    @SerializedName("title")
    private BlogRendered blogRenderedTitle;
    @SerializedName("date_gmt")
    private String datePost;
    private transient List<String> listPhotos;
    @SerializedName("content")
    private BlogRendered blogRenderedContent;
    @SerializedName("link")
    private String url;


    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public BlogRendered getBlogRenderedTitle() {
        return blogRenderedTitle;
    }

    public void setBlogRenderedTitle(BlogRendered blogRenderedTitle) {
        this.blogRenderedTitle = blogRenderedTitle;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public List<String> getListPhotos() {
        return listPhotos;
    }

    public BlogRendered getBlogRenderedContent() {
        return blogRenderedContent;
    }

    public void setBlogRenderedContent(BlogRendered blogRenderedContent) {
        this.blogRenderedContent = blogRenderedContent;
    }

    public void setListPhotos(List<String> listPhotos) {
        this.listPhotos = listPhotos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
