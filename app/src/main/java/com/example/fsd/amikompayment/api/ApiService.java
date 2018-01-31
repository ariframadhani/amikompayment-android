package com.example.fsd.amikompayment.api;

import com.example.fsd.amikompayment.user.Data;
import com.example.fsd.amikompayment.user.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // LOGIN
    @Headers("Accept: application/json")
    @POST("login")
    @FormUrlEncoded
    Call<User> LoginUser(@Field("username") String username,
                                @Field("password") String password);

    // REGISTER
    @Headers("Accept: application/json")
    @POST("register")
    @FormUrlEncoded
    Call<ResponseBody> RegisterUser(@Field("nama") String nama,
                                    @Field("username") String username,
                                    @Field("password") String password,
                                    @Field("phone") String phone);

    @Headers("Accept: application/json")
    @GET("profile")
    Call<User> getProfile (@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @POST("logout")
    Call<User> UserLogout (@Header("Authorization") String token);

}
