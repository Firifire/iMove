package com.a007inthecity.imove;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriveFragment extends Fragment {
    private View driveFragmentView;

    public DriveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        driveFragmentView = inflater.inflate(R.layout.fragment_drive, container, false);

        WebView mWazeMap = (WebView) driveFragmentView.findViewById(R.id.map_waze);
        WebSettings webSettings = mWazeMap.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setGeolocationEnabled(true);
        mWazeMap.loadUrl("file:///android_asset/map_page.html");
//        mWazeMap.loadUrl("https://www.waze.com/livemap");
        return driveFragmentView;
    }


}
