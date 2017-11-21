package me.rolgalan.marvelw.provider.boundary

import me.rolgalan.marvelw.model.Comic
import me.rolgalan.marvelw.model.ComicsList
import me.rolgalan.marvelw.model.SimpleExtraItem
import me.rolgalan.marvelw.model.implementation.MarvelComic
import me.rolgalan.marvelw.model.implementation.MarvelComicList
import me.rolgalan.marvelw.model.implementation.MarvelSimpleExtraItem
import me.rolgalan.marvelw.provider.DataInterface
import me.rolgalan.marvelw.server.model.*

/**
 * Created by Roldán Galán on 20/11/2017.
 *
 * This class transforms a server object into its equivalente in our local data model.
 *
 * This is useful to skip any nullable server values, and to transform server complex objects into
 * more simple models.
 */
class ComicsBoundary(listener: DataInterface<ComicsList>) : DataBoundary<ComicsList, ServerResult<ServerMarvelComic>>(listener) {

    override fun onResultsReceived(response: ServerResult<ServerMarvelComic>?) {

        if (response != null && response.data != null) {
            listenerOnReceived(map(response.data))
        } else {
            onError("Null list response")
        }

    }

    private fun map(server: ServerContainer<ServerMarvelComic>): ComicsList {
        val comics = MarvelComicList(server.total, server.offset, server.count)
        comics.addAll(server.results.map { map(it) })
        return comics
    }

    private fun map(server: ServerMarvelComic): Comic {

        return MarvelComic(server.id, server.title, server.description ?: "", server.isbn,
                map(server.thumbnail), map(server.characters), map(server.stories),
                map(server.series))
    }

    private fun map(server: ServerCollectionList?): List<SimpleExtraItem> {
        server?.let {
            return server.items?.filter { it != null }?.map { map(it) } ?: emptyList()
        }
        return emptyList()
    }

    private fun map(server: ServerCollectionList.CollectionItem): SimpleExtraItem {
        return MarvelSimpleExtraItem(0, server.name)
    }

    private fun map(server: ServerImage?): String {
        server?.let {
            //Marvel supports several image sizes. For now we just use portratit large
            return server.path + "/" + "portrait_large" + "." + server.extension
        }
        return ""

    }
}
