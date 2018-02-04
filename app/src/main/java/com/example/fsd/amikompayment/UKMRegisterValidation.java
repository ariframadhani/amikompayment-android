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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;
import com.example.fsd.amikompayment.api.RetrofitClient;
import com.example.fsd.amikompayment.transact.ukm.Errors;

import java.io.IOException;
import java.lang.annotation.Annotation;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class UKMRegisterValidation extends AppCompatActivity implements View.OnClickListener{
    String TOKEN, bearer;
    ApiService mApiService;
    SharedPreferences pref;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.namaAcara) TextView nama_acara;
    @BindView(R.id.institusi) TextView institusi;
    @BindView(R.id.token) TextView token;
    @BindView(R.id.harga) TextView harga;
//    @BindView(R.id.saldo_user) TextView saldo_user;
    @BindView(R.id.eTPassword) EditText password;
    @BindView(R.id.btnProses) Button btnProses;
    @BindView(R.id.progressBar2) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ukmregister_validation);
        ButterKnife.bind(this);

        mApiService = BaseApi.getApiService();
        progressBar.setVisibility(View.INVISIBLE);

        toolbar.setTitle("Validasi Token Pembayaran");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("MyPref", 0);
        TOKEN = pref.getString("api_token", null);
        bearer = "Bearer " + TOKEN;

        getInfoToken();

        btnProses.setOnClickListener(this);

    }

    // Register UKM
    public void getInfoToken(){
        Bundle bundle = getIntent().getExtras();
        nama_acara.setText(bundle.getString("nama_acara"));
        institusi.setText(bundle.getString("institusi"));
        token.setText(bundle.getString("token_acara"));
        harga.setText("Rp "+bundle.getString("harga_acara"));
//        saldo_user.setText("Rp "+pref.getString("saldo_user", null));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProses:
                progressBar.setVisibility(View.VISIBLE);
                btnProses.setEnabled(false);

                final String token_acara = token.getText().toString();
                final String password_user = password.getText().toString();

                prosesTransaksi(bearer, token_acara, password_user);
                break;
        }
    }

    public void prosesTransaksi(String auth, String token_acara, String password ){
        mApiService.sendTransactUKM(auth, token_acara, password)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (response.isSuccessful()){
                            Intent i = new Intent(getApplicationContext(), Dashboard.class);
                            Toast.makeText(getApplicationContext(), "Pembayaran Berhasil", Toast.LENGTH_SHORT).show();
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(i);
                            finish();

                        }else{

                            btnProses.setEnabled(true);
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
                        btnProses.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
}
