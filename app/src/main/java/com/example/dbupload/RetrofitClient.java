package com.example.dbupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static MyAPI getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.5/db-upload/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyAPI.class);
    }
}
