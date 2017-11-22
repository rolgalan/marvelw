package me.rolgalan.marvelw.model.implementation

import me.rolgalan.marvelw.model.Comic
import me.rolgalan.marvelw.model.SimpleExtraItem

/**
 * Created by Roldán Galán on 21/11/2017.
 */
data class MarvelComic(override val id: Long,
                       override val title: String,
                       override val description: String,
                       override val isbn: String,
                       override val imagePathThumbnail: String,
                       override val characters: List<SimpleExtraItem>,
                       override val stories: List<SimpleExtraItem>,
                       override val series: List<SimpleExtraItem>) : Comic {

    override fun getCharactersString(): String {
        return characters.joinToString { it.name }
    }
}