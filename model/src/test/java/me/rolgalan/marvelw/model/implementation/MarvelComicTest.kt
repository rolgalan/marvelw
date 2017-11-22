package me.rolgalan.marvelw.model.implementation

import junit.framework.Assert.assertEquals
import me.rolgalan.marvelw.model.Comic
import me.rolgalan.marvelw.model.SimpleExtraItem
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

    private fun createComic(characters: List<SimpleExtraItem>): Comic {
        return MarvelComic(10, "title", "desc", "1234",
                "asdf.png", characters, emptyList(), emptyList())
    }

    private fun getSimpleItemList(size: Int): List<SimpleExtraItem> {
        if (size == 0) return emptyList()
        val list = mutableListOf<SimpleExtraItem>()
        for (i: Long in 1L..size) {
            list.add(getSimpleItem(i, "Name_$i"))
        }

        return list.toList()
    }

    private fun getSimpleItem(id: Long, name: String): SimpleExtraItem {
        return MarvelSimpleExtraItem(id, name)
    }

}