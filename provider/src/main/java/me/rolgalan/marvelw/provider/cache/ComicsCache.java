package me.rolgalan.marvelw.provider.cache;

import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.model.ComicsList;
import me.rolgalan.marvelw.model.implementation.MarvelComicList;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public class ComicsCache extends Cache<Comic, ComicsList> {

    @Override
    protected ComicsList initList() {

        return new MarvelComicList(-1,-1,-1);
    }
}
