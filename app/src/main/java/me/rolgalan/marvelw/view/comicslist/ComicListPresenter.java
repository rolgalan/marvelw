package me.rolgalan.marvelw.view.comicslist;

import me.rolgalan.marvelw.model.ComicsList;
import me.rolgalan.marvelw.provider.DataInterface;
import me.rolgalan.marvelw.provider.DataProvider;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

class ComicListPresenter implements DataInterface<ComicsList> {
    private final ComicsListView view;

    ComicListPresenter(ComicsListView view) {

        this.view = view;
        loadData();
    }

    void loadData() {
        //TODO load/spinner while request
        DataProvider.getInstance().getCharacterComics(0, this);
    }

    @Override
    public void onReceived(ComicsList data) {
        if (view!=null){
            view.setDataList(data);
        }
    }

    @Override
    public void onError(String error) {
        if (view!=null){
            view.showMessage(error);
        }
    }
}
