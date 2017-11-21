package me.rolgalan.marvelw.model

/**
 * Created by Roldán Galán on 20/11/2017.
 */

interface PaginableList<K> : MutableList<K> {

    var totalPossibleSize: Int

    var offset: Int

    fun hasMoreItemsToLoad(): Boolean

}
