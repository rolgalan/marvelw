package me.rolgalan.marvelw.model.implementation

import me.rolgalan.marvelw.model.createComicsList
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by Roldán Galán on 23/11/2017.
 */
class MarvelComicListTest {

    @Test
    fun hasMoreItemsToLoad_true() {
        assertTrue(createComicsList(size = 3, possibleSize = 10).hasMoreItemsToLoad())
    }

    @Test
    fun hasMoreItemsToLoad_false() {
        assertFalse(createComicsList(size = 5, possibleSize = 5).hasMoreItemsToLoad())
    }

    @Test
    fun hasMoreItemsToLoad_empty_false() {
        assertFalse(createComicsList(size = 0, possibleSize = 0).hasMoreItemsToLoad())
    }

    @Test
    fun hasMoreItemsToLoad_empty_true() {
        assertTrue(createComicsList(size = 0, possibleSize = 5).hasMoreItemsToLoad())
    }

}