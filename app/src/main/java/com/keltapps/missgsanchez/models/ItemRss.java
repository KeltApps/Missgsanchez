package com.keltapps.missgsanchez.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by sergio on 3/01/16 for KelpApps.
 */
@Root(name = "item", strict = false)
public class ItemRss {


    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

    @Element(name = "link")
    private String link;

    @Element(name = "enclosure")
    private ContentRss content;

    @Element(name = "pubDate")
    private String date;

    public ItemRss() {
    }

    public ItemRss(String title, String description, String link, ContentRss content, String date) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public ContentRss getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
