package com.marvelcomics.utility;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by SHUCHI on 6/3/2016.
 */
public class APIAdapter {
    public static final String BASE_URL = "http://gateway.marvel.com/v1/public/";

    public Retrofit providesRestAdapter() {

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest;
                if (request.body() != null) {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    String body = buffer.readUtf8();

                }
                Log.e("URLLLLLL", request.urlString());

                newRequest = request.newBuilder()
                        .addHeader("Accept-Encoding", "*/*")
                        .build();
                Log.e("HEADERRRRR1", newRequest.headers().toString());
                com.squareup.okhttp.Response response = chain.proceed(newRequest);
                return response;
            }
        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(logging);
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

}
