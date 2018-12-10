package com.a007inthecity.imove;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarpoolFragment extends Fragment {

    private View mCarpoolFragment;
    private Button mBtnDriver, mBtnPassenger;

    public CarpoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCarpoolFragment = inflater.inflate(R.layout.fragment_carpool, container, false);
        mBtnDriver = mCarpoolFragment.findViewById(R.id.button_driver);
        mBtnPassenger = mCarpoolFragment.findViewById(R.id.button_passenger);

        mBtnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CarpoolActivity.class);
                intent.putExtra("isDriver", true);
                startActivity(intent);
            }
        });

        mBtnPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CarpoolActivity.class);
                intent.putExtra("isDriver", false);
                startActivity(intent);
            }
        });

        return mCarpoolFragment;
    }

}
