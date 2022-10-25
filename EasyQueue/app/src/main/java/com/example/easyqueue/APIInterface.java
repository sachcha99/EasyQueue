package com.example.easyqueue;



import com.example.easyqueue.ShedController.Shed;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;



public interface APIInterface {


    @POST("/api/Shed")
    Call<Shed> createUser(@Body Shed shed);

//    @GET("/api/Shed")
//    Call<UserList> doGetShedList(@Query("page") String page);


}
