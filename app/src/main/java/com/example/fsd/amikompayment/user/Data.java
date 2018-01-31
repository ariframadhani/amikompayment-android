package com.example.fsd.amikompayment.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by fsd on 1/29/2018.
 */

public class Data {
    @SerializedName("nama_lengkap")
    @Expose
    private String namaLengkap;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("api_token")
    @Expose
    private String api_token;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("saldo")
    @Expose
    private Integer saldo;
    @SerializedName("user_is")
    @Expose
    private String userIs;
    @SerializedName("is_official")
    @Expose
    private String isOfficial;
    @SerializedName("status")
    @Expose
    private String status;

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getUserIs() {
        return userIs;
    }

    public void setUserIs(String userIs) {
        this.userIs = userIs;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Data{" +
                "namaLengkap='" + namaLengkap + '\'' +
                ", username='" + username + '\'' +
                ", api_token='" + api_token + '\'' +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", saldo=" + saldo +
                ", userIs='" + userIs + '\'' +
                ", isOfficial='" + isOfficial + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
