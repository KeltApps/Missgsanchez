package com.keltapps.missgsanchez.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by sergio on 3/01/16 for KelpApps.
 */
@Root(name = "rss", strict = false)
@Namespace(reference = "http://search.yahoo.com/mrss/")
public class Rss {

    @Element
    private ChannelRss channel;

    public Rss() {
    }

    public Rss(ChannelRss channel) {
        this.channel = channel;
    }

    public ChannelRss getChannel() {
        return channel;
    }
}
