package com.example.fsd.amikompayment.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 1/30/2018.
 */

public class Errors {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private RestErrors errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                "message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
