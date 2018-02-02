package com.example.fsd.amikompayment.deposit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/2/2018.
 */

public class Deposit {

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
        return "Deposit{" +
                "data=" + data +
                '}';
    }
}
