package com.example.fsd.amikompayment.acara;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsd on 2/4/2018.
 */

public class Data {
    @SerializedName("nama_acara")
    @Expose
    private String namaAcara;
    @SerializedName("penyelengara")
    @Expose
    private String penyelengara;
    @SerializedName("token_acara")
    @Expose
    private String tokenAcara;
    @SerializedName("kategori_acara")
    @Expose
    private String kategoriAcara;
    @SerializedName("harga_acara")
    @Expose
    private Integer hargaAcara;

    public String getNamaAcara() {
        return namaAcara;
    }

    public void setNamaAcara(String namaAcara) {
        this.namaAcara = namaAcara;
    }

    public String getPenyelengara() {
        return penyelengara;
    }

    public void setPenyelengara(String penyelengara) {
        this.penyelengara = penyelengara;
    }

    public String getTokenAcara() {
        return tokenAcara;
    }

    public void setTokenAcara(String tokenAcara) {
        this.tokenAcara = tokenAcara;
    }

    public String getKategoriAcara() {
        return kategoriAcara;
    }

    public void setKategoriAcara(String kategoriAcara) {
        this.kategoriAcara = kategoriAcara;
    }

    public Integer getHargaAcara() {
        return hargaAcara;
    }

    public void setHargaAcara(Integer hargaAcara) {
        this.hargaAcara = hargaAcara;
    }

    @Override
    public String toString() {
        return "Data{" +
                "namaAcara='" + namaAcara + '\'' +
                ", penyelengara='" + penyelengara + '\'' +
                ", tokenAcara='" + tokenAcara + '\'' +
                ", kategoriAcara='" + kategoriAcara + '\'' +
                ", hargaAcara=" + hargaAcara +
                '}';
    }
}
