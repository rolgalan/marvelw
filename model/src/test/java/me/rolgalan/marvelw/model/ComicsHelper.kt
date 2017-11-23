package me.rolgalan.marvelw.model

import me.rolgalan.marvelw.model.implementation.MarvelComic
import me.rolgalan.marvelw.model.implementation.MarvelComicList
import me.rolgalan.marvelw.model.implementation.MarvelSimpleExtraItem

/**
 * Created by Roldán Galán on 23/11/2017.
 */

fun createComicsList(possibleSize: Int, size: Int = 3, offset: Int = 0): ComicsList {
    val list = MarvelComicList(possibleSize, offset)
    (0L until size).mapTo(list) { createComic(id = it) }
    return list
}

fun createComic(characters: List<SimpleExtraItem> = emptyList(), id: Long = 10): Comic =
        MarvelComic(id, "title", "desc", "1234",
                "asdf.png", characters, emptyList(), emptyList())

fun getSimpleItemList(size: Int): List<SimpleExtraItem> {
    if (size == 0) return emptyList()
    val list = (1L..size).map { getSimpleItem(it, "Name_$it") }

    return list.toList()
}

fun getSimpleItem(id: Long, name: String): SimpleExtraItem = MarvelSimpleExtraItem(id, name)
