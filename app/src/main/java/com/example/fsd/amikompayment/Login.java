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
import com.example.fsd.amikompayment.user.Data;
import com.example.fsd.amikompayment.user.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegister;
    EditText user, pass;
    ProgressBar progressBar;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = BaseApi.getApiService();

        user = (EditText) findViewById(R.id.eTUsername_login);
        pass = (EditText) findViewById(R.id.eTPassword_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.INVISIBLE);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:

                progressBar.setVisibility(View.VISIBLE);
//                Intent i = new Intent(getApplicationContext(), Dashboard.class);
//                startActivity(i);
//                finish();

                final String username = user.getText().toString().trim();
                final String password = pass.getText().toString().trim();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    btnLogin.setEnabled(false);
                    btnRegister.setEnabled(false);

                    loginUser(username, password);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Username dan password masih kosong", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnRegister:
                Intent rg = new Intent(getApplicationContext(), Register.class);
                startActivity(rg);
                finish();
                break;
        }
    }

    public void loginUser(String username, String password) {
        mApiService.LoginUser(username, password)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("response", "code = " + response.toString());

                        if (response.isSuccessful()){
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("api_token", response.body().getData().getApi_token());
                            editor.commit();

                            Intent i = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(i);
                            finish();

                        }else {
                            btnLogin.setEnabled(true);
                            btnRegister.setEnabled(true);
                            Toast.makeText(getApplicationContext(), "Authentifikasi gagal", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnLogin.setEnabled(true);
                        btnRegister.setEnabled(true);

                        Toast.makeText(getApplicationContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}




//        new Callback<ResponseBody>() {
//@Override
//public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//        progressBar.setVisibility(View.INVISIBLE);
//        Log.d("response", "code = " + response.toString());
//
//        if (response.isSuccessful()){
//        Log.d("response", "code = " + response.body().toString());
//        Intent i = new Intent(getApplicationContext(), Dashboard.class);
//        startActivity(i);
//        finish();
//        }else {
//        btnLogin.setEnabled(true);
//        btnRegister.setEnabled(true);
//        Toast.makeText(getApplicationContext(), "Authentifikasi gagal", Toast.LENGTH_SHORT).show();
//        }
//        }
//
//@Override
//public void onFailure(Call<ResponseBody> call, Throwable t) {
//        progressBar.setVisibility(View.INVISIBLE);
//        btnLogin.setEnabled(true);
//        btnRegister.setEnabled(true);
//
//        Toast.makeText(getApplicationContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();
//        Log.e("debug", "onFailure: ERROR > " + t.toString());
//        }

