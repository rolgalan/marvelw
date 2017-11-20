package me.rolgalan.marvelw.provider;

/**
 * Created by Roldán Galán on 20/11/2017.
 * Any class which wants to communicate with DataProvider to get some asynchronous data, must to
 * implement this interface.
 */
public interface DataInterface<T> {

    /**
     * Handle successful return of data
     */
    void onReceived(T data);

    /**
     * Handle error requests
     */
    void onError(String error);
}
