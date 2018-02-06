package com.example.fsd.amikompayment.detail_barang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/5/2018.
 */

public class DetailBelanja {

    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("kode_barang")
    @Expose
    private String kodeBarang;
    @SerializedName("harga_barang")
    @Expose
    private Integer hargaBarang;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public Integer getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(Integer hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    @Override
    public String toString() {
        return "DetailBelanja{" +
                "invoice='" + invoice + '\'' +
                ", kodeBarang='" + kodeBarang + '\'' +
                ", hargaBarang=" + hargaBarang +
                '}';
    }
}
