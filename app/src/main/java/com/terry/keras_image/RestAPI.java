package com.terry.keras_image;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * *
 * name     RestAPI
 * Creater  Terry
 * time     2018/4/5
 * *
 **/

public interface RestAPI {

    @POST("predict")
    @Multipart
    Call<Result> uploadFile(@Part MultipartBody.Part image);

}
