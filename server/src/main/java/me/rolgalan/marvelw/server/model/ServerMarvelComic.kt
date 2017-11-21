package me.rolgalan.marvelw.server.model

/**
 * Created by Roldán Galán on 20/11/2017.
 */

class ServerMarvelComic {

    var id: Long = 0
        internal set
    var title: String = ""
        internal set
    var description: String? = null
        internal set
    var resourceURI: String = ""
        internal set
    var isbn: String = ""
        internal set
    var format: String = ""
        internal set
    var characters: ServerCollectionList? = null
        internal set
    var stories: ServerCollectionList? = null
        internal set
    var series: ServerCollectionList? = null
        internal set
    var thumbnail: ServerImage? = null
        internal set
}
