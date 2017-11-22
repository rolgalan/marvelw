package me.rolgalan.marvelw.view.comicslist;

import android.util.Log;

import me.rolgalan.marvelw.model.ComicsList;
import me.rolgalan.marvelw.provider.DataInterface;
import me.rolgalan.marvelw.provider.DataProvider;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

class ComicListPresenter implements DataInterface<ComicsList> {

    private final ComicsListView view;
    private boolean isLoading = false;

    ComicListPresenter(ComicsListView view) {

        this.view = view;
    }

    void loadData() {
        //TODO load/spinner while request
        DataProvider.getInstance().getCharacterComics(this);
    }

    @Override
    public void onReceived(ComicsList data) {

        if (view != null) {
                view.setDataList(data);
        }
        onRequestEnded();
    }

    @Override
    public void onError(String error) {

        if (view != null) {
            view.showMessage(error);
            view.onRequestEnded();
        }
    }

    void loadMore() {

        if (!isLoading) {
            Log.i("ComicsList", "loadMoreButton");
            view.onLoadingMore();
            view.showProgress();
            isLoading = true;
            loadData();
        }
    }

    private void onRequestEnded() {

        if (view != null) {
            view.onRequestEnded();
        }
        isLoading = false;
    }
}
