package me.rolgalan.marvelw.server.model;

import java.util.List;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public class ServerMarvelComic {
    long id;
    String title;
    String description;
    String resourceURI;
    String isbn;
    String format;
    List<ServerCollectionList> characters;
    List<ServerCollectionList> stories;
    List<ServerCollectionList> series;
    ServerImage thumbnail;
/*


 */
}
