package com.a007inthecity.imove;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.a007inthecity.imove.Constants.MAPVIEW_BUNDLE_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusFragment extends Fragment implements OnMapReadyCallback{

    private View mBusFragmentView;
    private MapView mMapView;

    private ArrayList<LatLng> mBusStopsLocation = new ArrayList<>();
    private ArrayList<String> mBusStopsName = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBusFragmentView = inflater.inflate(R.layout.fragment_bus, container, false);
        mMapView = mBusFragmentView.findViewById(R.id.bus_map);

        initGoogleMap(savedInstanceState);

        return mBusFragmentView;
    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        initBusStops();
        placeMarkersOnMap(map);

        map.setMyLocationEnabled(true);
        //map.setTrafficEnabled(true);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void initBusStops(){
        mBusStopsName.add("Penang Sentral");
        mBusStopsLocation.add(new LatLng(5.394313599999999, 100.36705230000007));

        mBusStopsName.add("Jln Bagan Luar");
        mBusStopsLocation.add(new LatLng(5.4005455,100.36832559999993));

        mBusStopsName.add("Jln Kg Gajah");
        mBusStopsLocation.add(new LatLng(5.4162192,100.37323409999999));

        mBusStopsName.add("Jln Bagan Ajam");
        mBusStopsLocation.add(new LatLng(5.434289199999999,100.37944360000006));

        mBusStopsName.add("Jln Teluk Air Tawar");
        mBusStopsLocation.add(new LatLng(5.48467,100.38367399999993));

        mBusStopsName.add("Kepala Batas");
        mBusStopsLocation.add(new LatLng(5.5190801,100.46662489999994));

        mBusStopsName.add("Kompleks Dato Kailan");
        mBusStopsLocation.add(new LatLng(5.515485,100.43034799999998));
    }

    private void placeMarkersOnMap(GoogleMap map){
        for (int i = 0; i < mBusStopsLocation.size(); i++){
            map.addMarker(new MarkerOptions()
                    .position(mBusStopsLocation.get(i))
                    .title(mBusStopsName.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop_marker))
            );
        }


        map.addMarker(new MarkerOptions()
                        .position(new LatLng(5.452891292075856,100.38334522816035))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mBusStopsLocation.get(0),(float)12.0));

    }
}
