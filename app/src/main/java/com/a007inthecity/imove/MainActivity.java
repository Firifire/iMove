package com.a007inthecity.imove;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private BusFragment busFragment;
    private WalkFragment walkFragment;
    private DriveFragment driveFragment;
    private CarpoolFragment carpoolFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainNav = (BottomNavigationView)findViewById(R.id.main_nav);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);

        busFragment = new BusFragment();
        carpoolFragment = new CarpoolFragment();
        driveFragment = new DriveFragment();
        walkFragment = new WalkFragment();
        accountFragment = new AccountFragment();

        mMainNav.setSelectedItemId(R.id.nav_bus);
        setFragment(busFragment);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_bus:
                        setFragment(busFragment);
                        return true;
                    case R.id.nav_walk:
                        setFragment(walkFragment);
                        return true;
                    case R.id.nav_drive:
                        setFragment(driveFragment);
                        return true;
                    case R.id.nav_carpool:
                        setFragment(carpoolFragment);
                        return true;
                    case R.id.nav_account:
                        setFragment(accountFragment);
                        return true;
                    default:
                        setFragment(busFragment);
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();

    }

}
