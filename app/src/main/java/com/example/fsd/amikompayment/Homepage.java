package com.example.fsd.amikompayment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Homepage extends Fragment implements View.OnClickListener{

    ImageButton btnPembayaran, btnDeposit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);

            btnPembayaran = rootView.findViewById(R.id.btnMenuPembayaran);
            btnDeposit = rootView.findViewById(R.id.btnMenuDeposit);

            btnPembayaran.setOnClickListener(this);
            btnDeposit.setOnClickListener(this);

            return rootView;
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMenuPembayaran:
                Intent a = new Intent(getContext(), Pembayaran.class);
                startActivity(a);
                break;
            case R.id.btnMenuDeposit:
                Intent b = new Intent(getContext(), Deposit.class);
                startActivity(b);
                break;
        }
    }
}
