package com.example.fsd.amikompayment.deposit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/2/2018.
 */

public class Data {

    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nominal")
    @Expose
    private Integer nominal;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Data{" +
                "invoice='" + invoice + '\'' +
                ", username='" + username + '\'' +
                ", nominal=" + nominal +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
