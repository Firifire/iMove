package com.a007inthecity.imove;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class CarpoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras().getBoolean("isDriver")){
//            Toast.makeText(this, "I'm a driver is chosen", Toast.LENGTH_SHORT).show();
            setFragment(new CarpoolDriverFragment());
        }else {
//            Toast.makeText(this, "I'm a passenger is chosen", Toast.LENGTH_SHORT).show();
            setFragment(new CarpoolPassengerFragment());
        }
        setContentView(R.layout.activity_carpool);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.carpool_frame,fragment);
        fragmentTransaction.commit();
    }
}
