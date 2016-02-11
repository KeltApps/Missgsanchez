package com.keltapps.missgsanchez.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BlogItem {
    @SerializedName("id")
    private int idPost;
    @SerializedName("title")
    private JSONRendered jsonRenderedTitle;
    @SerializedName("date_gmt")
    private String datePost;
    private transient List<String> listPhotos;
    @SerializedName("content")
    private JSONRendered jsonRenderedContent;



    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public JSONRendered getJsonRenderedTitle() {
        return jsonRenderedTitle;
    }

    public void setJsonRenderedTitle(JSONRendered jsonRenderedTitle) {
        this.jsonRenderedTitle = jsonRenderedTitle;
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

    public JSONRendered getJsonRenderedContent() {
        return jsonRenderedContent;
    }

    public void setJsonRenderedContent(JSONRendered jsonRenderedContent) {
        this.jsonRenderedContent = jsonRenderedContent;
    }

    public void setListPhotos(List<String> listPhotos) {
        this.listPhotos = listPhotos;
    }


    public class JSONRendered {
        @SerializedName("rendered")
        private String string;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }

}
