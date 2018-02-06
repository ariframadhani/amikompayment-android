package com.example.fsd.amikompayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;
import com.example.fsd.amikompayment.detail_barang.DetailBarang;
import com.example.fsd.amikompayment.detail_barang.DetailBelanja;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranBarangToken extends AppCompatActivity implements View.OnClickListener {
    ApiService mApiService;
    String headerHead, textHeader, token, bearer;
    Integer idMenu;
    SharedPreferences pref;

    @BindView(R.id.btnProses)
    Button btnProses;
    @BindView(R.id.inputToken)
    EditText inputToken;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar;
    @BindView(R.id.headerText)
    TextView header;
    @BindView(R.id.headerText2) TextView header2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_barang);

        ButterKnife.bind(this);
        mApiService = BaseApi.getApiService();
        progressBar.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        idMenu = bundle.getInt("idMenu");

        if (idMenu == 1){
            customLayout("Citra Mart");
        }else {
            customLayout("Kantin");
        }

        pref = getSharedPreferences("MyPref", 0);
        bearer = "Bearer " + pref.getString("api_token", null);;

        btnProses.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProses:
                token = inputToken.getText().toString();

                if (token.equals("")){
                    Toast.makeText(getApplicationContext(), "Kode token belum diisi", Toast.LENGTH_SHORT).show();
                }else{
                    btnProses.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);

                    if (idMenu == 1){
                        getDetail(bearer, "citra-mart", token);
                    }else {
                        getDetail(bearer, "kantin", token);
                    }

                    btnProses.setEnabled(true);
                }
            break;
        }
    }

    public void getDetail(String auth, String kategori, String token){
        mApiService.getDetailBarang(auth, kategori, token)
                .enqueue(new Callback<DetailBarang>() {
                    @Override
                    public void onResponse(Call<DetailBarang> call, Response<DetailBarang> response) {
                        if (response.isSuccessful()){

                            progressBar.setVisibility(View.INVISIBLE);

                            Intent a = new Intent(getApplicationContext(), BarangTokenValidation.class);

                            a.putExtra("invoice", response.body().getData().getInvoiceTransact());
                            a.putExtra("tempat_belanja", response.body().getData().getUsername());
                            a.putExtra("total_harga", response.body().getData().getTotalHarga().toString());

                            startActivity(a);
                        }else{
                            switch (response.code()){
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Please relogin to get the information", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "Nomor pembayaran tidak ditemukan", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<DetailBarang> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void customLayout(String menu){
        toolbar.setTitle("Pembayaran "+menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headerHead = "Pembayaran "+menu;
        textHeader = "Masukan Nomor Pembayaran "+menu;
        header.setText(headerHead);
        header2.setText(textHeader);
    }
}
