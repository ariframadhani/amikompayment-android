package com.example.fsd.amikompayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;
import com.example.fsd.amikompayment.api.RetrofitClient;
import com.example.fsd.amikompayment.deposit.Errors;

import java.io.IOException;
import java.lang.annotation.Annotation;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class Deposit extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ApiService mApiService;
    String TOKEN, auth, value;
    int intValue;

    @BindView(R.id.etnominalDeposit) EditText nominal;
    @BindView(R.id.etpasswordDeposit) EditText password;
    @BindView(R.id.btnProsesDeposit) Button btnDeposit;
    @BindView(R.id.progressBar2) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        toolbar = (Toolbar) findViewById(R.id.toolbarDeposit);
        toolbar.setTitle("Top Up");
        setSupportActionBar(toolbar);

        mApiService = BaseApi.getApiService();
        ButterKnife.bind(this);

        progressBar.setVisibility(View.INVISIBLE);
        btnDeposit.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProsesDeposit:

                progressBar.setVisibility(View.VISIBLE);

                try{
                    if (!nominal.getText().toString().equals("")){
                        value = nominal.getText().toString();
                        intValue = Integer.parseInt(value);
                    }

                    final String password_user = password.getText().toString();

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    TOKEN = pref.getString("api_token", null);
                    auth = "Bearer " + TOKEN;

                    prosesDeposit(auth, intValue, password_user);

                }catch (NumberFormatException e){
                    intValue = 0;
                }


                break;
        }
    }

    public void prosesDeposit(String auth, Integer nominal, String password){

        mApiService.sendDeposit(auth, nominal, password).enqueue(new Callback<com.example.fsd.amikompayment.deposit.Deposit>() {
            @Override
            public void onResponse(Call<com.example.fsd.amikompayment.deposit.Deposit> call, Response<com.example.fsd.amikompayment.deposit.Deposit> response) {
                if (response.isSuccessful()){

                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d("response", "deposit body = " + response.body().getData().toString());
                    Toast.makeText(getApplicationContext(), "Deposit sukses, please check your profile", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(i);
                    finish();
                }else{

                    progressBar.setVisibility(View.INVISIBLE);
                    Converter<ResponseBody, Errors> errorsConverter =
                            RetrofitClient.retrofit.responseBodyConverter(Errors.class, new Annotation[0]);
                    try{
                        Errors errors = errorsConverter.convert(response.errorBody());

                        if (errors.getInfo() != null){
                            String errInfo = errors.getInfo().toString();
                            errInfo = errInfo.replaceAll("\\[","").replaceAll("\\]","");

                            Toast.makeText(getApplicationContext(), errInfo, Toast.LENGTH_SHORT).show();
                        }else if (errors.getErrors().getNominal() != null){
                            String errNominal = errors.getErrors().getNominal().toString();
                            errNominal = errNominal.replaceAll("\\[","").replaceAll("\\]","");

                            Toast.makeText(getApplicationContext(), errNominal, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<com.example.fsd.amikompayment.deposit.Deposit> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Couldn't reach the server, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
