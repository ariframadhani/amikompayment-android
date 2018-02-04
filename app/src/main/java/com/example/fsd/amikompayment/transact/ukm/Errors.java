package com.example.fsd.amikompayment.transact.ukm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/4/2018.
 */

public class Errors {
    @SerializedName("info")
    @Expose
    private String info;

    @SerializedName("errors")
    @Expose
    private RestErrors errors;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public RestErrors getErrors() {
        return errors;
    }

    public void setErrors(RestErrors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Errors{" +
                "info='" + info + '\'' +
                ", errors=" + errors +
                '}';
    }
}
