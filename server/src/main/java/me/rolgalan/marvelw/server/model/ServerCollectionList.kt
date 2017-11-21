package me.rolgalan.marvelw.server.model

/**
 * Created by Roldán Galán on 20/11/2017.
 */

class ServerCollectionList {

    var available: Int = 0
        internal set
    var collectionURI: String = ""
        internal set
    var items: List<CollectionItem>? = null
        internal set

    class CollectionItem {

        var resourceURI: String = ""
            internal set
        var name: String = ""
            internal set
        var type: String? = null
            internal set
    }
}
