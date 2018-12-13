package com.a007inthecity.imove;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarpoolPassengerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private Dialog mRewardDialog;
    private View mCarpoolPassengerFragment;
    private ZXingScannerView mScannerView;
    private Button mButtonArrived;

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

        mButtonArrived = (Button) mCarpoolPassengerFragment.findViewById(R.id.button_arrived);
        mButtonArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewardDialog();
            }
        });

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

    private void showRewardDialog(){
        mRewardDialog = new Dialog(getContext());
        mRewardDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRewardDialog.setContentView(R.layout.dialog_reward);
        mRewardDialog.setTitle("Reward dialog");

        TextView dialogMessage= (TextView)mRewardDialog.findViewById(R.id.dialog_text);
        dialogMessage.setText("You earned 100 points from your last carpool ride");

        Button dialogButton = mRewardDialog.findViewById(R.id.dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        mRewardDialog.show();
    }
}
