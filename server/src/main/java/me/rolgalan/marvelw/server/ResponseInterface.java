package me.rolgalan.marvelw.server;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public interface ResponseInterface<T> {

    void onResultsReceived(T response);

    void onError(String error);
}