package com.example.fsd.amikompayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;
import com.example.fsd.amikompayment.api.RetrofitClient;
import com.example.fsd.amikompayment.transact.barang.Errors;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class BarangTokenValidation extends AppCompatActivity implements View.OnClickListener{
    ApiService mApiService;
    Bundle data;
    String TOKEN, auth;

    @BindView(R.id.invoice) TextView invoiceBarang;
    @BindView(R.id.tempat_belanja) TextView tempatBelanja;
    @BindView(R.id.totalHarga) TextView totalHarga;
    @BindView(R.id.detailBelanja) TableLayout tableDetailBelanja;

    @BindView(R.id.btnProses) Button btnProses;
    @BindView(R.id.eTPassword) EditText password;
    @BindView(R.id.progressBar2) ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_token_validation);

        ButterKnife.bind(this);
        mApiService = BaseApi.getApiService();

        progressBar.setVisibility(View.INVISIBLE);
        getInfoDetail();

        btnProses.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProses:
                progressBar.setVisibility(View.VISIBLE);
                btnProses.setEnabled(false);

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                TOKEN = pref.getString("api_token", null);
                auth = "Bearer " + TOKEN;
                final String getPassword = password.getText().toString();

                doTransaction(auth, data.getString("invoice"), getPassword);
                break;
        }
    }

    private void getInfoDetail(){
        data = getIntent().getExtras();

        invoiceBarang.setText(data.getString("invoice"));
        tempatBelanja.setText(data.getString("tempat_belanja"));
        totalHarga.setText("Rp "+data.getString("total_harga"));
    }

    private void doTransaction(String auth, String invoice, String password){
        mApiService.sendTransactBarang(auth, invoice, password)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            btnProses.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);

                            Intent i = new Intent(getApplicationContext(), PembayaranSuccess.class);
                            startActivity(i);
                            finish();

                        }else{
                            btnProses.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);

                            Converter<ResponseBody, Errors> errorsConverter =
                                    RetrofitClient.retrofit.responseBodyConverter(Errors.class, new Annotation[0]);
                            try{
                                Errors errors = errorsConverter.convert(response.errorBody());

                                if (errors.getInfo() != null){
                                    String errInfo = errors.getInfo().toString();
                                    errInfo = errInfo.replaceAll("\\[","").replaceAll("\\]","");

                                    Toast.makeText(getApplicationContext(), errInfo, Toast.LENGTH_SHORT).show();
                                }else if(errors.getErrors().getPassword() != null){

                                    String errPassword = errors.getErrors().getPassword().toString();
                                    errPassword = errPassword.replaceAll("\\[","").replaceAll("\\]","");

                                    Toast.makeText(getApplicationContext(), errPassword, Toast.LENGTH_SHORT).show();
                                }

                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnProses.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
