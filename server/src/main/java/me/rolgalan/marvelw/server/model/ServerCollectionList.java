package me.rolgalan.marvelw.server.model;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public class ServerCollectionList {

    int available;
    String collectionURI;
    List<CollectionItem> items;

    public int getAvailable() {

        return available;
    }

    public String getCollectionURI() {

        return collectionURI;
    }

    public List<CollectionItem> getItems() {

        return items;
    }

    public static class CollectionItem {

        String resourceURI;
        String name;
        @Nullable
        String type;

        public String getResourceURI() {

            return resourceURI;
        }

        public String getName() {

            return name;
        }

        @Nullable
        public String getType() {

            return type;
        }
    }
}
