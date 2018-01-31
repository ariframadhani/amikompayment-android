package com.example.fsd.amikompayment.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 1/29/2018.
 */

public class User {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "User{" +
                "data=" + data +
                '}';
    }
}
