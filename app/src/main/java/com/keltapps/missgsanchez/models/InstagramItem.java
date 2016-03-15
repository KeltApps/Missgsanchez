package com.keltapps.missgsanchez.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class InstagramItem {
    @SerializedName("status")
    String status;
    @SerializedName("items")
    List<InstagramSubItem> listSubItem;
    @SerializedName("more_available")
    boolean moreAvailable;


    public class InstagramSubItem {
        @SerializedName("location")
        InstagramLocation location;
        @SerializedName("images")
        InstagramResolutionImages instagramImages;
        @SerializedName("likes")
        InstagramLikes instagramLikes;
        @SerializedName("id")
        String id;
        @SerializedName("user")
        InstagramUser instagramUser;

        public class InstagramLocation{
            @SerializedName("name")
            String name;

            public String getname() {
                return name;
            }

            public void setname(String location) {
                this.name = name;
            }
        }

        public class InstagramResolutionImages{
            @SerializedName("low_resolution")
            InstagramImages lowResolution;
            @SerializedName("thumbnail")
            InstagramImages thumbnail;
            @SerializedName("standard_resolution")
            InstagramImages standard_resolution;

            public InstagramImages getLowResolution() {
                return lowResolution;
            }

            public void setLowResolution(InstagramImages lowResolution) {
                this.lowResolution = lowResolution;
            }

            public InstagramImages getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(InstagramImages thumbnail) {
                this.thumbnail = thumbnail;
            }

            public InstagramImages getStandard_resolution() {
                return standard_resolution;
            }

            public void setStandard_resolution(InstagramImages standard_resolution) {
                this.standard_resolution = standard_resolution;
            }
        }


        public InstagramResolutionImages getInstagramImages() {
            return instagramImages;
        }

        public void setInstagramImages(InstagramResolutionImages instagramImages) {
            this.instagramImages = instagramImages;
        }

        public InstagramLikes getInstagramLikes() {
            return instagramLikes;
        }

        public void setInstagramLikes(InstagramLikes instagramLikes) {
            this.instagramLikes = instagramLikes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public InstagramUser getInstagramUser() {
            return instagramUser;
        }

        public void setInstagramUser(InstagramUser instagramUser) {
            this.instagramUser = instagramUser;
        }
    }

    public class InstagramImages {
        @SerializedName("url")
        String url;
        @SerializedName("width")
        String width;
        @SerializedName("height")
        String height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }

    public class InstagramLikes {
        @SerializedName("count")
        int count;
    }

    public class InstagramUser {
        @SerializedName("username")
        String userName;
        @SerializedName("profile_picture")
        String profilePicture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<InstagramSubItem> getListSubItem() {
        return listSubItem;
    }

    public void setListSubItem(List<InstagramSubItem> listSubItem) {
        this.listSubItem = listSubItem;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public void setMoreAvailable(boolean moreAvailable) {
        this.moreAvailable = moreAvailable;
    }
}
