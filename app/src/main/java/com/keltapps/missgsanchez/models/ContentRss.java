package com.keltapps.missgsanchez.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by sergio on 3/01/16 for KelpApps.
 */
@Root(name = "content", strict = false)
public class ContentRss {

    @Attribute(name = "url")
    private String url;

    public ContentRss() {
    }

    public ContentRss(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
