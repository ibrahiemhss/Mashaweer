package com.mashaweer.ibrahim.mashaweer.Network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ibrahim on 19/01/18.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl( baseUrl )
                    .addConverterFactory( GsonConverterFactory.create())
                    .build();
        }
            return retrofit;
        }



}