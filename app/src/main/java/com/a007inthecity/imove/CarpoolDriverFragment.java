package com.a007inthecity.imove;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarpoolDriverFragment extends Fragment {

    private View mCarpoolDriverFragment;
    private ImageView mQrCodeHolder;

    public CarpoolDriverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCarpoolDriverFragment = inflater.inflate(R.layout.fragment_carpool_driver, container, false);
        mQrCodeHolder = mCarpoolDriverFragment.findViewById(R.id.driver_qr_code);

        mQrCodeHolder.setImageBitmap(getQRCodeImage("John Doe",300,300));

        return mCarpoolDriverFragment;
    }


    /**
    * This method takes the text to be encoded, the width and height of the QR Code,
    * and returns the QR Code in the form of a byte array.
    */
    private Bitmap getQRCodeImage(String text, int width, int height){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,width,height);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
