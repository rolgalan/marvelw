package me.rolgalan.marvelw.view.comicslist;

import java.util.List;

import me.rolgalan.marvelw.model.Comic;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

interface ComicsListView {

    void showMessage(String message);

    void notifyDatasetChanged();

    void setDataList(List<Comic> list);

    /*void showProgress();

    void hideProgress();

    void onLoadingMore();

    void onRequestEnded();
    */
}
