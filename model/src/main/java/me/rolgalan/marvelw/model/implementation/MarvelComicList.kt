package me.rolgalan.marvelw.model.implementation

import me.rolgalan.marvelw.model.Comic
import me.rolgalan.marvelw.model.ComicsList

/**
 * Created by Roldán Galán on 21/11/2017.
 */
class MarvelComicList(override var totalPossibleSize: Int,
                      override var offset: Int) : ArrayList<Comic>(), ComicsList {

    override fun hasMoreItemsToLoad(): Boolean {
        return size < totalPossibleSize
    }
}