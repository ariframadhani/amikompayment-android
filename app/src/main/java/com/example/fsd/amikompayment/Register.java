package com.example.fsd.amikompayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;
import com.example.fsd.amikompayment.api.RetrofitClient;
import com.example.fsd.amikompayment.user.Data;
import com.example.fsd.amikompayment.user.Errors;
import com.example.fsd.amikompayment.user.RestErrors;
import com.example.fsd.amikompayment.user.User;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegister;
    EditText nama, user, pass, phone;
    ProgressBar progressBar;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        mApiService = BaseApi.getApiService();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        nama = (EditText) findViewById(R.id.eTNamalengkap);
        pass = (EditText) findViewById(R.id.eTPassword);
        user = (EditText) findViewById(R.id.eTUsername);
        phone = (EditText) findViewById(R.id.eTPhone);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                progressBar.setVisibility(View.VISIBLE);

                if (nama.getText().toString().equals("")
                        || user.getText().toString().equals("")
                        || pass.getText().toString().equals("")
                        || phone.getText().toString().equals("")) {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Data form tidak valid", Toast.LENGTH_SHORT).show();

                }else {
                    buttonControl(false);
                    registerUser(nama.getText().toString(),
                            user.getText().toString().replace(" ", ""),
                            pass.getText().toString(),
                            phone.getText().toString().replace(" ", ""));
                }

                break;

            case R.id.btnLogin:
                Intent lgn = new Intent(getApplicationContext(), Login.class);
                startActivity(lgn);
                finish();
                break;
        }
    }

    private void registerUser(String nama_lengkap, String username, String password, String tlp){
        mApiService.RegisterUser(nama_lengkap,username,password,tlp)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("response", "code = " + response.toString());
                        progressBar.setVisibility(View.INVISIBLE);

                        if (response.isSuccessful()){

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("api_token", response.body().getData().getApi_token());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Welcome "+response.body().getData().getNamaLengkap(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Converter<ResponseBody, Errors> errorConverter =
                                    RetrofitClient.retrofit.responseBodyConverter(Errors.class, new Annotation[0]);
                            try {
                                Errors error = errorConverter.convert(response.errorBody());

                                if (error.getErrors().getNama() != null){
                                    String errNama = error.getErrors().getNama().toString();
                                    errNama = errNama.replaceAll("\\[","").replaceAll("\\]","");

                                    buttonControl(true);
                                    Toast.makeText(getApplicationContext(), errNama, Toast.LENGTH_SHORT).show();

                                }else if (error.getErrors().getUsername() != null){
                                    String errUsername = error.getErrors().getUsername().toString();
                                    errUsername = errUsername.replaceAll("\\[","").replaceAll("\\]","");

                                    buttonControl(true);
                                    Toast.makeText(getApplicationContext(), errUsername, Toast.LENGTH_SHORT).show();

                                }else if(error.getErrors().getPassword() != null){
                                    String errPassword = error.getErrors().getPassword().toString();
                                    errPassword = errPassword.replaceAll("\\[","").replaceAll("\\]","");

                                    buttonControl(true);
                                    Toast.makeText(getApplicationContext(), errPassword, Toast.LENGTH_SHORT).show();

                                }else if(error.getErrors().getPhone() != null){
                                    String errPhone = error.getErrors().getPhone().toString();
                                    errPhone = errPhone.replaceAll("\\[","").replaceAll("\\]","");

                                    buttonControl(true);
                                    Toast.makeText(getApplicationContext(), errPhone, Toast.LENGTH_SHORT).show();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        buttonControl(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void buttonControl(Boolean status){
        btnLogin.setEnabled(status);
        btnRegister.setEnabled(status);
    }
}
