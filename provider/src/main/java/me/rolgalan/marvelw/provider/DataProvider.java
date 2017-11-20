package me.rolgalan.marvelw.provider;

import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.model.ComicsList;
import me.rolgalan.marvelw.provider.boundary.ComicsBoundary;
import me.rolgalan.marvelw.provider.cache.ComicsCache;
import me.rolgalan.marvelw.server.ApiManager;
import me.rolgalan.marvelw.server.ResponseInterface;
import me.rolgalan.marvelw.server.model.ServerMarvelComic;
import me.rolgalan.marvelw.server.model.ServerResult;

/**
 * Created by Roldán Galán on 20/11/2017.
 * <p>
 * This class should be the only way of the view (app module) to fetch data from the server.
 * <p>
 * If permanent storage will be required in the future, a new module handling the database operations
 * should be added. But the logic to decide whether the server or the database is asked, should be
 * implemented here.
 */

public class DataProvider {

    private static final DataProvider instance = new DataProvider();
    private final static int DEFAULT_CHARACTER_ID = 1009220;
    /*
        This cache is only implemented for handling the comics of a single character.
        Current specifications doesn't allow selecting different characters.
        If this changes in the future, this cache should be able to store the character id whose
        comics is representing.
     */
    private ComicsCache comicsCache = new ComicsCache();

    private DataProvider() {

    }

    public static DataProvider getInstance() {

        return instance;
    }

    public void getCharacterComics(int offset, DataInterface<ComicsList> listener) {

        getCharacterComics(DEFAULT_CHARACTER_ID, offset, listener);
    }

    private void getCharacterComics(int characterId, int offset,
                                    final DataInterface<ComicsList> listener) {

        if (comicsCache.getCurrentOffset() >= offset) {
            listener.onReceived(comicsCache.getItems());
        } else {
            ApiManager.getInstance()
                    .getCharacterComics(characterId, offset,
                            createCharacterComicsListener(listener));
        }
    }

    public void getComic(long comicId, DataInterface<Comic> listener) {

        Comic cached = comicsCache.getItemById(comicId);
        if (cached != null) {
            listener.onReceived(cached);
        } else {
            listener.onError("Not found");
            //TODO request comic to server by id
        }
    }

    public void getComicByPosition(int position, DataInterface<Comic> listener) {

        Comic cached = comicsCache.getItemByPosition(position);
        if (cached != null) {
            listener.onReceived(cached);
        } else {
            listener.onError("Not found");
        }
    }

    /**
     * Creates a custom listener for handling the characters comic response.
     * This is only required for saving the server response into the cache, and propagating
     * the result to the listener which called to the DataProvider in the first place.
     *
     * @param listener
     * @return
     */
    private ResponseInterface<ServerResult<ServerMarvelComic>> createCharacterComicsListener(
            final DataInterface<ComicsList> listener) {

        return new ComicsBoundary(new DataInterface<ComicsList>() {

            @Override
            public void onReceived(ComicsList data) {

                comicsCache.setOtherList(data);
                if (listener != null) {
                    listener.onReceived(data);
                }
            }

            @Override
            public void onError(String error) {

                if (listener != null) {
                    listener.onError(error);
                }
            }
        });
    }
}
