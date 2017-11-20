package me.rolgalan.marvelw.server.model;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public class ServerResult<T> extends ServerBaseObject {
    String etag;
    String copyright;
    String attributionText;
    String attributionHTML;
    ServerContainer<T> data;

    public String getEtag() {
        return etag;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public ServerContainer<T> getData() {
        return data;
    }
}