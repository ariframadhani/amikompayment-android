package com.example.fsd.amikompayment.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fsd on 1/30/2018.
 */

public class RestErrors {

    @SerializedName("nama")
    @Expose
    private List<String> nama = null;
    @SerializedName("username")
    @Expose
    private List<String> username = null;
    @SerializedName("password")
    @Expose
    private List<String> password = null;
    @SerializedName("phone")
    @Expose
    private List<String> phone = null;

    public List<String> getNama() {
        return nama;
    }

    public void setNama(List<String> nama) {
        this.nama = nama;
    }

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }
}
