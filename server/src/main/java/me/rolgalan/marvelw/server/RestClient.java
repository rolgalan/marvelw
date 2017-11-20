package me.rolgalan.marvelw.server;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.rolgalan.marvelw.server.model.ServerMarvelComic;
import me.rolgalan.marvelw.server.model.ServerResult;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

class RestClient {

    private final static String BASE_URL = "https://gateway.marvel.com/v1/public/";
    private final static String TAG = "Request";
    private static ApiInterface apiInterface;

    public synchronized static ApiInterface getClient() {

        if (apiInterface == null) {
            final OkHttpClient clientAux = new OkHttpClient.Builder().build();

            OkHttpClient.Builder builder = clientAux.newBuilder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(getApiKeyInterceptor());

            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(new StethoInterceptor());
            }

            Retrofit client = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = client.create(ApiInterface.class);
        }

        return apiInterface;
    }

    private static Interceptor getApiKeyInterceptor() {

        return new Interceptor() {

            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {

                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                ServerUtils.TimestampHash tsHash = ServerUtils.getTimestampHash();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", ServerUtils.getPublicKey())
                        .addQueryParameter("ts", tsHash.getTimestamp())
                        .addQueryParameter("hash", tsHash.getHash())
                        .build();

                Request.Builder requestBuilder = original.newBuilder().url(url);

                Log.i(TAG, "Request url: " + url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    public interface ApiInterface {

        @GET("characters/{characterId}/comics")
        Call<ServerResult<ServerMarvelComic>> getCharacterComics(
                @Path("characterId") int characterId, @Query("offset") int offset);

        /*
        Maybe we require these request in the near future:
        @GET("characters/{characterId}")
        Call<ServerResult<ServerMarvelCharacter>> getCharacter(@Path("characterId") int characterId);

        @GET("comics/{comicId}")
        Call<ServerResult<ServerMarvelComic>> getComic(@Query("comicId") int comicId);
        */
    }
}
