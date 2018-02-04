package com.example.fsd.amikompayment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PembayaranUKM extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    @BindView(R.id.btnRegister) ImageButton btnRegister;
    @BindView(R.id.btnEvent) ImageButton btnEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_ukm);

        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarUkm);
        toolbar.setTitle("Pembayaran UKM");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegister.setOnClickListener(this);
        btnEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                Intent a = new Intent(getApplicationContext(), PembayaranUKMToken.class);
                a.putExtra("id_menu", 1);  // REGISTER UKM
                startActivity(a);
                break;

            case R.id.btnEvent:
                Intent b = new Intent(getApplicationContext(), PembayaranUKMToken.class);
                b.putExtra("id_menu", 2);  // EVENT UKM
                startActivity(b);

                break;
        }
    }
}
