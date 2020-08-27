package com.example.dbupload;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MyAPI {

    @Multipart
    @POST("Api.php?apicall=upload")
    Call<UploadResponse> uploadImage(
            @Part MultipartBody.Part part,
            @Part("desc") RequestBody requestBody
    );
}
