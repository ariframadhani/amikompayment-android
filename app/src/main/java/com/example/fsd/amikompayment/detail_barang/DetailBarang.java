package com.example.fsd.amikompayment.detail_barang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/5/2018.
 */

public class DetailBarang {
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
        return "DetailBarang{" +
                "data=" + data +
                '}';
    }
}
