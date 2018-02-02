package com.example.fsd.amikompayment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Pembayaran extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ImageButton ukm, cm, kantin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pembayaran);

        toolbar = (Toolbar) findViewById(R.id.toolbarOnMenu);
        toolbar.setTitle("Pembayaran");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ukm = (ImageButton) findViewById(R.id.btnMenuPembayaranukm);
        cm = (ImageButton) findViewById(R.id.btnMenuPembayarancm);
        kantin = (ImageButton) findViewById(R.id.btnMenuPembayarankantin);

        ukm.setOnClickListener(this);
        cm.setOnClickListener(this);
        kantin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMenuPembayaranukm:
                Intent a = new Intent(getApplicationContext(), PembayaranUKM.class);
                startActivity(a);
                break;
        }
    }
}
