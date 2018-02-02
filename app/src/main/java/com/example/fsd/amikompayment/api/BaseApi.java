package com.example.fsd.amikompayment.api;

/**
 * Created by fsd on 1/29/2018.
 */

public class BaseApi {

//    public static final String BASE_URL_API = "http://127.0.0.1:8000/api/";
    public static final String BASE_URL_API = "http://192.168.43.210/api/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }

}
