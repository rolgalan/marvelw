package me.rolgalan.marvelw.server;

import android.util.Log;

import me.rolgalan.marvelw.server.model.ServerMarvelComic;
import me.rolgalan.marvelw.server.model.ServerResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public class ApiManager {

    private final static String TAG = "Request";
    private static ApiManager instance;

    public static synchronized ApiManager getInstance() {

        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public void getCharacterComics(int characterId, int offset,
                                   final ResponseInterface<ServerResult<ServerMarvelComic>> listener) {

        Call<ServerResult<ServerMarvelComic>> call = RestClient.getClient()
                .getCharacterComics(characterId, offset);
        call.enqueue(new MyCallback(listener));
    }

    /**
     * Generic callback for common error handling.
     * TODO Map server errors into human-friendly texts.
     */
    private static class MyCallback<T> implements Callback<T> {

        private final ResponseInterface<T> listener;

        private MyCallback(ResponseInterface listener) {

            this.listener = listener;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {

            Log.d(TAG, "MyCallback.onResponse success " + (response != null
                                                           ? response.isSuccessful()
                                                           : "responseNULL"));
            if (response != null) {

                if (response.isSuccessful() && response.body() != null) {
                    listener.onResultsReceived(response.body());
                } else {
                    Log.e(TAG,
                            "MyCallback.onResponse error code (" + response.code() + ") " + response
                                    .errorBody());
                    if (response.errorBody() != null) {
                        Log.i(TAG, "onResponse errorbody: " + response.errorBody().toString());
                    }
                    listener.onError("Unknown sever error " + response.code());
                }

            } else {
                Log.e(TAG, "MyCallback.onResponse null");
                listener.onError("Invalid data received");
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {

            Log.e(TAG, "MyCallback.onFailure " + t);
            //t.printStackTrace();
            if (!t.getMessage().equals("Canceled")) {
                listener.onError("Error requesting data to the server");
            }
        }
    }
}
