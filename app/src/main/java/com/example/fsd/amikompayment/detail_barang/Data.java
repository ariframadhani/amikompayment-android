package com.example.fsd.amikompayment.detail_barang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fsd on 2/5/2018.
 */

public class Data {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("invoice_transact")
    @Expose
    private String invoiceTransact;
    @SerializedName("id_tempat")
    @Expose
    private String idTempat;

    @SerializedName("tempat")
    @Expose
    private String username;
    @SerializedName("detail_belanja")
    @Expose
    private ArrayList<DetailBelanja> detailBelanja = null;
    @SerializedName("total_harga")
    @Expose
    private Integer totalHarga;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceTransact() {
        return invoiceTransact;
    }

    public void setInvoiceTransact(String invoiceTransact) {
        this.invoiceTransact = invoiceTransact;
    }

    public String getIdTempat() {
        return idTempat;
    }

    public void setIdTempat(String idTempat) {
        this.idTempat = idTempat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<DetailBelanja> getDetailBelanja() {
        return detailBelanja;
    }

    public void setDetailBelanja(ArrayList<DetailBelanja> detailBelanja) {
        this.detailBelanja = detailBelanja;
    }

    public Integer getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Integer totalHarga) {
        this.totalHarga = totalHarga;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", invoiceTransact='" + invoiceTransact + '\'' +
                ", idTempat='" + idTempat + '\'' +
                ", username='" + username + '\'' +
                ", detailBelanja=" + detailBelanja +
                ", totalHarga=" + totalHarga +
                '}';
    }
}
