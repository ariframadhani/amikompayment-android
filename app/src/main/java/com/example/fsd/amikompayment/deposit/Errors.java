package com.example.fsd.amikompayment.deposit;

import com.example.fsd.amikompayment.user.RestErrors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/2/2018.
 */

public class Errors {

    @SerializedName("info")
    @Expose
    private String info;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private com.example.fsd.amikompayment.deposit.RestErrors errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.example.fsd.amikompayment.deposit.RestErrors getErrors() {
        return errors;
    }

    public void setErrors(com.example.fsd.amikompayment.deposit.RestErrors errors) {
        this.errors = errors;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Errors{" +
                "info='" + info + '\'' +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
