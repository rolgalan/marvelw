package me.rolgalan.marvelw.provider.boundary;

import android.util.Log;

import me.rolgalan.marvelw.provider.DataInterface;
import me.rolgalan.marvelw.server.ResponseInterface;

/**
 * Created by Roldán Galán on 20/11/2017.
 * <p>
 * This class implements ResponseInterface and transforms the data type received into a different type.
 * Works as a boundary between ResponseInterface (currently used only by the server, but in the future
 * any other external source, should implement this interface to communicate with the app) and
 * DataInterface, used by the view (app module) to request data.
 */
abstract class DataBoundary<T, K> implements ResponseInterface<K> {

    private final static String TAG = "Boundary";
    private final DataInterface<T> listener;

    DataBoundary(DataInterface<T> listener) {

        this.listener = listener;
    }

    @Override
    public abstract void onResultsReceived(K response);

    @Override
    public void onError(String error) {

        Log.e(TAG, "DataBoundary.onError: " + error);
        if (listener != null) {
            listener.onError(error);
        } else {
            Log.w(TAG, "onError. Warning: NO listener attatched to " + getClass().getSimpleName());
        }
    }

    void listenerOnReceived(T data) {

        Log.e(TAG, "DataBoundary.listenerOnReceived");
        if (listener != null) {
            listener.onReceived(data);
        } else {
            Log.w(TAG,
                    "listenerOnReceived. Warning: NO listener attatched to " + getClass().getSimpleName());
        }
    }
}