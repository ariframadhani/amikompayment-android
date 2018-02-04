package com.example.fsd.amikompayment.acara;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/4/2018.
 */

public class Acara {
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
        return "Acara{" +
                "data=" + data +
                '}';
    }
}
