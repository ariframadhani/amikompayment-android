package com.example.fsd.amikompayment;

import android.content.Intent;
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

import com.example.fsd.amikompayment.acara.Acara;
import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranUKMToken extends AppCompatActivity implements View.OnClickListener {
    ApiService mApiService;
    String headerHead, textHeader, token;
    Integer idMenu;

    @BindView(R.id.btnProses) Button btnProses;
    @BindView(R.id.inputToken) EditText inputToken;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar2) ProgressBar progressBar;
    @BindView(R.id.headerText) TextView header;
    @BindView(R.id.headerText2) TextView header2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_ukm_token);

        ButterKnife.bind(this);
        mApiService = BaseApi.getApiService();
        progressBar.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        idMenu = bundle.getInt("id_menu");

        if (idMenu == 1){
            toolbar.setTitle("Pembayaran UKM Register");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            headerHead = "Pembayaran UKM Register";
            textHeader = "Masukan Nomor Token Register";
            header.setText(headerHead);
            header2.setText(textHeader);


        }else{
            toolbar.setTitle("Pembayaran UKM Event");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            headerHead = "Pembayaran UKM Event";
            textHeader = "Masukan Nomor Token Event";
            header.setText(headerHead);
            header2.setText(textHeader);

        }


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
                        getAcara("register", token);
                    }else{
                        getAcara("event", token);
                    }

                    btnProses.setEnabled(true);
                }

                break;
        }
    }

    public void getAcara(String kategori, String token){
        mApiService.getAcara(kategori, token)
                .enqueue(new Callback<Acara>() {
                    @Override
                    public void onResponse(Call<Acara> call, Response<Acara> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (response.isSuccessful()){

                            Intent i = new Intent(getApplicationContext(), UKMTokenValidation.class);
                            i.putExtra("nama_acara", response.body().getData().getNamaAcara());
                            i.putExtra("institusi", response.body().getData().getPenyelengara());
                            i.putExtra("kategori_acara", response.body().getData().getKategoriAcara());
                            i.putExtra("token_acara", response.body().getData().getTokenAcara());
                            i.putExtra("harga_acara", response.body().getData().getHargaAcara().toString());
                            startActivity(i);
                        }else{

                            Toast.makeText(getApplicationContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Acara> call, Throwable t) {
                        btnProses.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onLayout(){

    }
}
