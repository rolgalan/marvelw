package me.rolgalan.marvelw.model.implementation

import junit.framework.Assert.assertEquals
import me.rolgalan.marvelw.model.createComic
import me.rolgalan.marvelw.model.getSimpleItemList
import org.junit.Test

/**
 * Created by Roldán Galán on 22/11/2017.
 */
class MarvelComicTest {

    @Test
    fun getCharactersString_empty() {
        assertEquals("", createComic(getSimpleItemList(0)).getCharactersString())
    }

    @Test
    fun getCharactersString_two() {

        assertEquals("Name_1, Name_2, Name_3", createComic(getSimpleItemList(3)).getCharactersString())
    }
}