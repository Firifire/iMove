package com.a007inthecity.imove;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QrScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private View mFragmentQrScan;
    private boolean boarded = false;
    private Dialog rewardDialog;

    public QrScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentQrScan = inflater.inflate(R.layout.fragment_qr_scanner, container, false);
        mScannerView = mFragmentQrScan.findViewById(R.id.qr_scanner_view);

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        return mFragmentQrScan;
    }

    @Override
    public void handleResult(Result result) {
//        Toast.makeText(getContext(),result.getText(),Toast.LENGTH_SHORT).show();
        if (!result.getText().equals("301"))
            return;
        if (boarded){
            showRewardDialog();
        }
        boarded = true;
        TextView txtInfo = getActivity().findViewById(R.id.qr_info);
        txtInfo.setText("You're on bus " + result.getText() + "\nScan again while deboarding");
        ImageView imgBus = getActivity().findViewById(R.id.bus_image);
        imgBus.setImageResource(R.drawable.bus_image);
        mScannerView.resumeCameraPreview(this);
    }

    private void showRewardDialog(){
        rewardDialog = new Dialog(getContext());
        rewardDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rewardDialog.setContentView(R.layout.dialog_reward);
        rewardDialog.setTitle("Reward dialog");

        TextView dialogMessage= (TextView)rewardDialog.findViewById(R.id.dialog_text);
        dialogMessage.setText("You earned 150 points from your last bus ride");

        Button dialogButton = rewardDialog.findViewById(R.id.dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        rewardDialog.show();
    }

}
