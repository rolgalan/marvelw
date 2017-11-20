package me.rolgalan.marvelw.provider.boundary;

import java.util.List;

import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.model.ComicsList;
import me.rolgalan.marvelw.model.SimpleExtraItem;
import me.rolgalan.marvelw.provider.DataInterface;
import me.rolgalan.marvelw.server.model.ServerCollectionList;
import me.rolgalan.marvelw.server.model.ServerContainer;
import me.rolgalan.marvelw.server.model.ServerImage;
import me.rolgalan.marvelw.server.model.ServerMarvelComic;
import me.rolgalan.marvelw.server.model.ServerResult;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public class ComicsBoundary extends DataBoundary<ComicsList, ServerResult<ServerMarvelComic>> {

    public ComicsBoundary(DataInterface<ComicsList> listener) {

        super(listener);
    }

    @Override
    public void onResultsReceived(ServerResult<ServerMarvelComic> response) {

        if (response != null && response.getData() != null) {
            ComicsList list = map(response.getData());
            listenerOnReceived(list);
        } else {
            onError("Null list response");
        }

    }

    private ComicsList map(ServerContainer<ServerMarvelComic> data) {
        //TODO implement
        return null;
    }

    private Comic map(ServerMarvelComic server) {
        //TODO implement
        return null;
    }

    private SimpleExtraItem map(ServerCollectionList.CollectionItem server) {
        //TODO implement
        return null;
    }

    private List<SimpleExtraItem> map(ServerCollectionList server) {
        //TODO implement
        return null;
    }

    private String map(ServerImage server) {
        //TODO implement
        return null;
    }
}
