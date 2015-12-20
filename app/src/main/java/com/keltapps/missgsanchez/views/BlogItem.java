package com.keltapps.missgsanchez.views;

import java.util.Date;


public class BlogItem {
    private String imageLink;
    private String tittle;
    private Date datePost;
    private int counterComments;

    public BlogItem() {
    }

    public BlogItem(String imageLink, String tittle, Date datePost, int counterComments) {
        this.imageLink = imageLink;
        this.tittle = tittle;
        this.datePost = datePost;
        this.counterComments = counterComments;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public int getCounterComments() {
        return counterComments;
    }

    public void setCounterComments(int counterComments) {
        this.counterComments = counterComments;
    }
}
