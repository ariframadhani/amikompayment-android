package com.example.fsd.amikompayment.transact.barang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fsd on 2/6/2018.
 */

public class RestErrors {

    @SerializedName("password")
    @Expose
    private List<String> password;

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RestErrors{" +
                "password=" + password +
                '}';
    }
}
