package me.rolgalan.marvelw.provider.boundary

import android.util.Log

import me.rolgalan.marvelw.provider.DataInterface
import me.rolgalan.marvelw.server.ResponseInterface

/**
 * Created by Roldán Galán on 20/11/2017.
 *
 * This class implements ResponseInterface and transforms the data type received into a different type.
 * Works as a boundary between ResponseInterface (currently used only by the server, but in the future
 * any other external source, should implement this interface to communicate with the app) and
 * DataInterface, used by the view (app module) to request data.
 */
abstract class DataBoundary<in T, K>(private val listener: DataInterface<T>?) : ResponseInterface<K> {
    private val TAG = "Boundary"

    abstract override fun onResultsReceived(response: K?)

    override fun onError(error: String) {

        Log.e(TAG, "DataBoundary.onError: " + error)
        listener?.onError(error)
    }

    fun listenerOnReceived(data: T) {

        Log.e(TAG, "DataBoundary.listenerOnReceived")
        listener?.onReceived(data)
    }
}