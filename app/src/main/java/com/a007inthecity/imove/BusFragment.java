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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
public class BusFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener{

    private View mBusFragmentView;
    private MapView mMapView;
    private Button mScanButton;

    private ArrayList<LatLng> mBusStopsLocation_1 = new ArrayList<>();
    private ArrayList<String> mBusStopsName_1 = new ArrayList<>();
    private ArrayList<LatLng> mBusStopsLocation_2 = new ArrayList<>();
    private ArrayList<String> mBusStopsName_2 = new ArrayList<>();

    private GoogleMap map;
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
        mScanButton = mBusFragmentView.findViewById(R.id.button_scan_bus_qr);

        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Button clicked",Toast.LENGTH_SHORT).show();
            }
        });


        initBusRoute_1();
        initBusRoute_2();

        initGoogleMap(savedInstanceState);

        Spinner mRouteSpinner = mBusFragmentView.findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> mRouteAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_items, R.array.bus_route_number);
        ArrayAdapter<CharSequence> mRouteAdapter = ArrayAdapter.createFromResource(getContext(),R.array.bus_route_number,R.layout.spinner_items);
        mRouteSpinner.setAdapter(mRouteAdapter);
        mRouteAdapter.setDropDownViewResource(R.layout.spinner_items);
//        mRouteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRouteSpinner.setAdapter(mRouteAdapter);
        mRouteSpinner.setOnItemSelectedListener(this);


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
        this.map = map;

//        placeMarkersOnMap(map,mBusStopsLocation_1,mBusStopsName_1);

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

    private void initBusRoute_1(){
        mBusStopsName_1.add("Penang Sentral");
        mBusStopsLocation_1.add(new LatLng(5.394313599999999, 100.36705230000007));

        mBusStopsName_1.add("Jln Bagan Luar");
        mBusStopsLocation_1.add(new LatLng(5.4005455,100.36832559999993));

        mBusStopsName_1.add("Jln Kg Gajah");
        mBusStopsLocation_1.add(new LatLng(5.4162192,100.37323409999999));

        mBusStopsName_1.add("Jln Bagan Ajam");
        mBusStopsLocation_1.add(new LatLng(5.434289199999999,100.37944360000006));

        mBusStopsName_1.add("Jln Teluk Air Tawar");
        mBusStopsLocation_1.add(new LatLng(5.48467,100.38367399999993));

        mBusStopsName_1.add("Kepala Batas");
        mBusStopsLocation_1.add(new LatLng(5.5190801,100.46662489999994));

        mBusStopsName_1.add("Kompleks Dato Kailan");
        mBusStopsLocation_1.add(new LatLng(5.515485,100.43034799999998));
    }

    private void initBusRoute_2(){
        mBusStopsName_2.add("Penang Sentral");
        mBusStopsLocation_2.add(new LatLng(5.394313599999999, 100.36705230000007));

        mBusStopsName_2.add("Jln Bagan Luar");
        mBusStopsLocation_2.add(new LatLng(5.4005455,100.36832559999993));

        mBusStopsName_2.add("Sg Puyu");
        mBusStopsLocation_2.add(new LatLng(5.4496066,100.39912240000001));

        mBusStopsName_2.add("Sg Lokan");
        mBusStopsLocation_2.add(new LatLng(5.4495157,100.41028979999999));

        mBusStopsName_2.add("Kampung Nyior Sebatang");
        mBusStopsLocation_2.add(new LatLng(5.466360799999999,100.45448269999997));

        mBusStopsName_2.add("Pokok Sena");
        mBusStopsLocation_2.add(new LatLng(5.489044,100.46296600000005));
    }

    private void placeMarkersOnMap(GoogleMap map, ArrayList<LatLng> busStopsLocation, ArrayList<String> busStopsName){

        map.clear();

        for (int i = 0; i < busStopsLocation.size(); i++){
            map.addMarker(new MarkerOptions()
                    .position(busStopsLocation.get(i))
                    .title(busStopsName.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop_marker))
            );
        }


        map.addMarker(new MarkerOptions()
                        .position(new LatLng(5.452891292075856,100.38334522816035))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(busStopsLocation.get(0),(float)12.0));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getItemAtPosition(i).toString().equals("601")) {
//            Toast.makeText(getContext(),"601 is selected",Toast.LENGTH_SHORT).show();
            placeMarkersOnMap(map,mBusStopsLocation_1,mBusStopsName_1);
        }else if (adapterView.getItemAtPosition(i).toString().equals("602")){
            placeMarkersOnMap(map,mBusStopsLocation_2,mBusStopsName_2);
//            Toast.makeText(getContext(),adapterView.getItemAtPosition(i).toString()  + " is selected",Toast.LENGTH_SHORT).show();
        }else{
            map.clear();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
