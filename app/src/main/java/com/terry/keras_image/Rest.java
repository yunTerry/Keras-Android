package com.terry.keras_image;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * *
 * name     Rest
 * Creater  Terry
 * time     2018/4/5
 * *
 **/

public class Rest {

    private static RestAPI restAPI;

    public static RestAPI getRestApi() {
        if (restAPI == null) {
            restAPI = new Retrofit.Builder()
                    .baseUrl("http://192.168.199.119:5000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RestAPI.class);
        }
        return restAPI;
    }
}
