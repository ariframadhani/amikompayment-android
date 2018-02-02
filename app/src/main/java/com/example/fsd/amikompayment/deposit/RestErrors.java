package com.example.fsd.amikompayment.deposit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fsd on 2/2/2018.
 */

public class RestErrors {
    @SerializedName("nominal")
    @Expose
    private List<String> nominal = null;

    @SerializedName("password")
    @Expose
    private List<String> password = null;

    public List<String> getNominal() {
        return nominal;
    }

    public void setNominal(List<String> nominal) {
        this.nominal = nominal;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RestErrors{" +
                "nominal=" + nominal +
                ", password=" + password +
                '}';
    }
}
