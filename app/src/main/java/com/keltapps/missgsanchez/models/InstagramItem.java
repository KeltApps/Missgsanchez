package com.keltapps.missgsanchez.models;

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
        @SerializedName("caption")
        Caption caption;
        @SerializedName("created_time")
        int time;

        public class InstagramLocation {
            @SerializedName("name")
            String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public InstagramLocation getLocation() {
            return location;
        }

        public void setLocation(InstagramLocation location) {
            this.location = location;
        }

        public class InstagramResolutionImages {
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
        }


        public InstagramResolutionImages getInstagramImages() {
            return instagramImages;
        }

        public void setInstagramImages(InstagramResolutionImages instagramImages) {
            this.instagramImages = instagramImages;
        }


        public class InstagramLikes {
            @SerializedName("count")
            int count;


            public void setCount(int count) {
                this.count = count;
            }

            public int getCount() {
                return count;
            }
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

        public class InstagramUser {
            @SerializedName("username")
            String userName;
            @SerializedName("profile_picture")
            String profilePicture;

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserName() {
                return userName;
            }


            public void setProfilePicture(String profilePicture) {
                this.profilePicture = profilePicture;
            }

            public String getProfilePicture() {
                return profilePicture;
            }
        }

        public InstagramUser getInstagramUser() {
            return instagramUser;
        }

        public void setInstagramUser(InstagramUser instagramUser) {
            this.instagramUser = instagramUser;
        }

        public class Caption{
            @SerializedName("text")
            String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public Caption getCaption() {
            return caption;
        }

        public void setCaption(Caption caption) {
            this.caption = caption;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public List<InstagramSubItem> getListSubItem() {
        return listSubItem;
    }

    public void setListSubItem(List<InstagramSubItem> listSubItem) {
        this.listSubItem = listSubItem;
    }

}
