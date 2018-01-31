package com.example.fsd.amikompayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fsd.amikompayment.api.ApiService;
import com.example.fsd.amikompayment.api.BaseApi;
import com.example.fsd.amikompayment.api.RetrofitClient;
import com.example.fsd.amikompayment.user.Data;
import com.example.fsd.amikompayment.user.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment implements View.OnClickListener{
    ApiService mApiService;
    TextView nama,user,token,saldo,phone;
    ProgressBar progressBar;
    Button btnLogout;
    String TOKEN, bearer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mApiService = BaseApi.getApiService();

        progressBar = rootView.findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.VISIBLE);

        nama = rootView.findViewById(R.id.tvNama);
        user = rootView.findViewById(R.id.tvUsername);
        token = rootView.findViewById(R.id.tvToken);
        saldo = rootView.findViewById(R.id.tvSaldo);
        phone = rootView.findViewById(R.id.tvPhone);

        btnLogout = rootView.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(this);
        getProfileUser();

        return rootView;
    }

    public void getProfileUser(){
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        TOKEN = pref.getString("api_token", null);

        bearer = "Bearer " + TOKEN;
        mApiService.getProfile(bearer)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("response", "code = " + response.toString());

                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d("api", "check > " + response.body().getData());
                            nama.setText(response.body().getData().getNamaLengkap());
                            user.setText(response.body().getData().getUsername());
                            token.setText(response.body().getData().getToken());
                            saldo.setText("Rp "+response.body().getData().getSaldo().toString());
                            phone.setText(response.body().getData().getPhone());

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogout:
                progressBar.setVisibility(View.VISIBLE);
                prosesLogout();

                break;
        }
    }

    private void prosesLogout(){
        mApiService.UserLogout(bearer)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("response", "code = " + response.toString());
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.VISIBLE);
                            Intent i = new Intent(getActivity(), Login.class);
                            startActivity(i);
                            getActivity().finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "Couldn't reach the server", Toast.LENGTH_SHORT).show();
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}