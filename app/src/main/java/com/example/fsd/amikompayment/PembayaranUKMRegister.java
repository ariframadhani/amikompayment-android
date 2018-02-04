package com.example.fsd.amikompayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fsd.amikompayment.acara.Acara;
import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fsd.amikompayment.R.id.start;
import static com.example.fsd.amikompayment.R.id.toolbar;

public class PembayaranUKMRegister extends AppCompatActivity implements View.OnClickListener {
    ApiService mApiService;
    String TOKEN;

    @BindView(R.id.btnProses) Button btnProses;
    @BindView(R.id.inputToken) EditText inputToken;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar2) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_ukmregister);

        ButterKnife.bind(this);
        mApiService = BaseApi.getApiService();
        progressBar.setVisibility(View.INVISIBLE);

        toolbar.setTitle("Token UKM Register");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnProses.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProses:

                final String token = inputToken.getText().toString();

                if (token.equals("")){
                    Toast.makeText(getApplicationContext(), "Kode token belum diisi", Toast.LENGTH_SHORT).show();
                }else{
                    btnProses.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);

                    getAcara("register", token);
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

                            Intent i = new Intent(getApplicationContext(), UKMRegisterValidation.class);
                            i.putExtra("nama_acara", response.body().getData().getNamaAcara());
                            i.putExtra("institusi", response.body().getData().getPenyelengara());
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
}
