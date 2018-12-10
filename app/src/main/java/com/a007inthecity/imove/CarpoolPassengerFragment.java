package com.a007inthecity.imove;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarpoolPassengerFragment extends Fragment implements ZXingScannerView.ResultHandler {


    private View mCarpoolPassengerFragment;
    private ZXingScannerView mScannerView;

    public CarpoolPassengerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCarpoolPassengerFragment = inflater.inflate(R.layout.fragment_carpool_passenger, container, false);
        mScannerView = mCarpoolPassengerFragment.findViewById(R.id.driver_qr_scanner);

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

        return  mCarpoolPassengerFragment;
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getContext(), result.getText(), Toast.LENGTH_SHORT).show();
        if (result.getText().equals("John Doe")){
            mCarpoolPassengerFragment.findViewById(R.id.driver_info_panel).setVisibility(View.VISIBLE);
        }

        mScannerView.resumeCameraPreview(this);
    }
}
