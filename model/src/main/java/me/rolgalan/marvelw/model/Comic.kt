package me.rolgalan.marvelw.model

/**
 * Created by Roldán Galán on 20/11/2017.
 */

interface Comic : Identifiable {

    val title: String

    val description: String

    val isbn: String

    val imagePathThumbnail: String

    val characters: List<SimpleExtraItem>

    val stories: List<SimpleExtraItem>

    val series: List<SimpleExtraItem>

}
