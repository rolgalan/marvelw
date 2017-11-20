package me.rolgalan.marvelw.model;

import java.util.List;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public interface Comic {

    Long getId();

    String getTitle();

    String getDescription();

    String getIsbn();

    String getImagePathThumbnail();

    List<SimpleExtraItem> getCharacters();

    List<SimpleExtraItem> getStories();

    List<SimpleExtraItem> getSeries();

}
