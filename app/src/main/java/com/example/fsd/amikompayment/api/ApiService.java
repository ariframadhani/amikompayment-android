package com.example.fsd.amikompayment.api;

import com.example.fsd.amikompayment.acara.Acara;
import com.example.fsd.amikompayment.deposit.Deposit;
import com.example.fsd.amikompayment.detail_barang.DetailBarang;
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
    Call<User> RegisterUser(@Field("nama") String nama,
                                    @Field("username") String username,
                                    @Field("password") String password,
                                    @Field("phone") String phone);

    // GET PROFILE
    @Headers("Accept: application/json")
    @GET("profile")
    Call<User> getProfile (@Header("Authorization") String token);

    // POST LOGOUT
    @Headers("Accept: application/json")
    @POST("logout")
    Call<User> UserLogout (@Header("Authorization") String token);

    // POST DEPOSIT
    @Headers("Accept: application/json")
    @POST("deposit")
    @FormUrlEncoded
    Call<Deposit> sendDeposit (@Header("Authorization") String token,
                               @Field("nominal") Integer nominal,
                               @Field("password") String password);

    // GET ACARA
    @Headers("Accept: application/json")
    @GET("acara/{kategori}/{token}")
    Call<Acara> getAcara (@Header("Authorization") String api_token,
                          @Path("kategori") String kategori,
                          @Path("token") String token_acara);

    // POST TRANSACT UKKM
    @Headers("Accept: application/json")
    @POST("transact-ukm")
    @FormUrlEncoded
    Call<ResponseBody> sendTransactUKM (@Header("Authorization") String api_token,
                                        @Field("token") String token_acara,
                                        @Field("password") String password);

    // GET DETAIL TRAMSACT BARANG
    @Headers("Accept: application/json")
    @GET("transact-barang/{kategori}/{invoice}")
    Call<DetailBarang> getDetailBarang (@Header("Authorization") String api_token,
                                        @Path("kategori") String kategori,
                                        @Path("invoice") String invoice);

    // SEND PAYMENT BARANG
    @Headers("Accept: application/json")
    @POST("payment-barang")
    @FormUrlEncoded
    Call<ResponseBody> sendTransactBarang (@Header("Authorization") String api_token,
                                           @Field("invoice") String invoice,
                                           @Field("password") String password);

}
