package me.rolgalan.marvelw.provider.cache

import me.rolgalan.marvelw.model.Comic
import me.rolgalan.marvelw.model.implementation.MarvelComic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Created by Roldán Galán on 22/11/2017.
 */
class CacheTest {

    @Test
    @Throws(Exception::class)
    fun addItem() {
        //After an item is added, it should be reflected in the size and should be returned by getItemById
        val cache = ComicsCache()
        val comic = createComic(4)
        cache.addItem(createComic(1))
        cache.addItem(comic)
        cache.addItem(createComic(12))
        assertEquals(3, cache.size)
        assertEquals(comic, cache.getItemById(4))
    }

    @Test
    @Throws(Exception::class)
    fun setOtherList() {
        TODO("implement")
    }

    @Test
    @Throws(Exception::class)
    fun getItems() {
        TODO("implement")
    }

    @Test
    @Throws(Exception::class)
    fun getItemById_nonExisting() {
        val cache = ComicsCache()
        cache.addItem(createComic(8))
        cache.addItem(createComic(18))
        cache.addItem(createComic(312))
        assertNull(cache.getItemById(10))
    }
    @Test
    @Throws(Exception::class)
    fun getItemById_ok() {
        val cache = ComicsCache()
        val comic = createComic(50)
        cache.addItem(createComic(18))
        cache.addItem(comic)
        cache.addItem(createComic(312))
        assertEquals(comic, cache.getItemById(50))
    }

    @Test
    @Throws(Exception::class)
    fun getItemByPosition_outOfBounds() {
        val cache = ComicsCache()
        cache.addItem(createComic(18))
        cache.addItem(createComic(19))
        assertNull(cache.getItemByPosition(20))
    }

    @Test
    @Throws(Exception::class)
    fun getCurrentOffset() {
        TODO("implement")
    }

    @Test
    @Throws(Exception::class)
    fun getSize_empty() {
        val cache = ComicsCache()
        assertEquals(0, cache.size.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getSize_two() {
        val cache = ComicsCache()
        cache.addItem(createComic(1))
        cache.addItem(createComic(2))
        assertEquals(2, cache.size)
    }

    @Test
    @Throws(Exception::class)
    fun clear() {

        val cache = ComicsCache()
        cache.addItem(createComic(1))
        cache.addItem(createComic(2))
        assertEquals(2, cache.size)
        cache.clear()
        assertEquals(0, cache.size)
    }

    private fun createComic(id: Long): Comic {
        return MarvelComic(id, "title", "desc", "1234", "asdf.png", emptyList(), emptyList(), emptyList())
    }
}