package com.a007inthecity.imove;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ScanBusQRActivity extends AppCompatActivity{

    private QrScannerFragment mQrScannerFragment = new QrScannerFragment();
    private TextView mTxtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.qr_scanner_holder, mQrScannerFragment);
        fragmentTransaction.commit();

        mTxtInfo = (TextView) findViewById(R.id.qr_info);

        setContentView(R.layout.activity_scan_bus_qr);
    }

    public TextView getTxtInfo(){
        return this.mTxtInfo;
    }
}
