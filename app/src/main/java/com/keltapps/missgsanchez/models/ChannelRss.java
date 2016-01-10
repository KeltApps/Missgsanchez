package com.keltapps.missgsanchez.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by sergio on 3/01/16 for KelpApps.
 */
@Root(name = "channel", strict = false)
public class ChannelRss {

        @ElementList(inline = true)
        private List<ItemRss> items;

        public ChannelRss() {
        }

        public ChannelRss(List<ItemRss> items) {
            this.items = items;
        }

        public List<ItemRss> getItems() {
            return items;
        }
}
