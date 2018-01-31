package com.example.fsd.amikompayment.api;

/**
 * Created by fsd on 1/29/2018.
 */

public class BaseApi {

    public static final String BASE_URL_API = "http://10.0.2.2:8000/api/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }

}
