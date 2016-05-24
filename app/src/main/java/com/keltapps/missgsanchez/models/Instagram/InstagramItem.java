package com.keltapps.missgsanchez.models.Instagram;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class InstagramItem {
    @SerializedName("status")
    private String status;
    @SerializedName("more_available")
    private boolean moreAvailable;
    @SerializedName("items")
    private List<InstagramSubItem> listSubItem;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public void setMoreAvailable(boolean moreAvailable) {
        this.moreAvailable = moreAvailable;
    }


    public List<InstagramSubItem> getListSubItem() {
        return listSubItem;
    }

    public void setListSubItem(List<InstagramSubItem> listSubItem) {
        this.listSubItem = listSubItem;
    }

}
