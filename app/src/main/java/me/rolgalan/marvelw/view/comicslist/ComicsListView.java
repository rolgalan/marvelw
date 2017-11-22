package me.rolgalan.marvelw.view.comicslist;

import me.rolgalan.marvelw.model.ComicsList;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

interface ComicsListView {

    void showMessage(String message);

    void setDataList(ComicsList list);

   void showProgress();

    void hideProgress();

    void onLoadingMore();

    void onRequestEnded();
}
