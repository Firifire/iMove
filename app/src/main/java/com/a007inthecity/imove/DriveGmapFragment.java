package com.a007inthecity.imove;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.a007inthecity.imove.Constants.MAPVIEW_BUNDLE_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriveGmapFragment extends Fragment implements OnMapReadyCallback {

    private int[] mPoints = {15,60,55,40};
    private ArrayList<Integer> mRoutesDistance = new ArrayList<>();
    private ArrayList<Integer> mRoutesDuration = new ArrayList<>();
    private static int color_value = 100;
    private static int starting_char = 65;
    private GoogleMap mMap;
    private MapView mDriveGmapFrame;

    private ViewGroup routeGroup;
//    private FragmentActivity mDriveMapFragmentActivity;
    private View mFragmentDriveGmap;

    ArrayList markerPoints = new ArrayList();

    public DriveGmapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentDriveGmap =  inflater.inflate(R.layout.fragment_drive_gmap, container, false);
        mDriveGmapFrame = (MapView) mFragmentDriveGmap  .findViewById(R.id.drive_map_frame);
        routeGroup = (ViewGroup) mFragmentDriveGmap.findViewById(R.id.possible_routes);
        initGoogleMap(savedInstanceState);

        return mFragmentDriveGmap;
    }

    private void addRouteInfoTextView(Object route_tag, int route_color, int distance_in_meters, int duration_in_seconds, int points){

        float distance_in_km = distance_in_meters/ 1000;
        int duration_in_minutes = duration_in_seconds/ 60;

        LinearLayout newRouteInfo = new LinearLayout(getContext());
        newRouteInfo.setOrientation(LinearLayout.HORIZONTAL);
        newRouteInfo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        newRouteInfo.setWeightSum(4f);

        TextView tvRouteName = new TextView(getContext());
        tvRouteName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
        tvRouteName.setText("Route " + route_tag.toString());
        tvRouteName.setTextSize(30.0f);
        tvRouteName.setTextColor(route_color);

        TextView tvDistance = new TextView(getContext());
        tvDistance.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
        tvDistance.setText(Float.toString(distance_in_km) + " km");
        tvDistance.setTextSize(20.0f);
        tvDistance.setTextColor(route_color);

        TextView tvDuration = new TextView(getContext());
        tvDuration.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
        tvDuration.setText(Integer.toString(duration_in_minutes) + " mins");
        tvDuration.setTextSize(20.0f);
        tvDuration.setTextColor(route_color);

        TextView tvPoints = new TextView(getContext());
        tvPoints.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
        tvPoints.setText(points + " pts");
        tvPoints.setTextSize(20.0f);
        tvPoints.setTextColor(route_color);

        newRouteInfo.addView(tvRouteName);
        newRouteInfo.addView(tvDistance);
        newRouteInfo.addView(tvDuration);
        newRouteInfo.addView(tvPoints);

        routeGroup.addView(newRouteInfo);

        ProgressBar newLine = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        newLine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        newLine.setProgressTintList(ColorStateList.valueOf(route_color));
        newLine.setProgress(100);
        routeGroup.addView(newLine);
    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mDriveGmapFrame.onCreate(mapViewBundle);

        mDriveGmapFrame.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mDriveGmapFrame.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDriveGmapFrame.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDriveGmapFrame.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDriveGmapFrame.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        this.mMap = map;


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    mMap.clear();
                    routeGroup.removeAllViews();
                    mRoutesDistance.clear();
                    mRoutesDuration.clear();
                }

                // Adding new item to the ArrayList
                markerPoints.add(latLng);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions().draggable(true);

                // Setting the position of the marker
                options.position(latLng);

                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }

                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);

                    color_value = 100;
                    starting_char = 65;
                }
            }
        });
//        placeMarkersOnMap(map,mBusStopsLocation_1,mBusStopsName_1);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(5.394313599999999, 100.36705230000007),(float)11.0330));
        map.setMyLocationEnabled(true);
        map.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Toast.makeText(getContext(), polyline.getTag() + " coordinates" + polyline.getPoints() +
                                                    " is clicked", Toast.LENGTH_SHORT).show();
            }
        });



//        map.setTrafficEnabled(true);
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
                parseForDistance(jObject);

                Log.e("Distance in meters: ", mRoutesDistance.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
//                Log.e("Direction API result size",Integer.toString(result.size()));
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
//                    Log.e("Direction API result size Lat:",point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
//                    Log.e("Direction API result size Lng:",point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.argb(200, color_value+=50,color_value+=50,color_value+=50));
                lineOptions.geodesic(true);
                lineOptions.clickable(true);


                Polyline polyline = mMap.addPolyline(lineOptions);
                polyline.setTag((char) starting_char++);
                polyline.setClickable(true);

                addRouteInfoTextView(polyline.getTag(),polyline.getColor(),mRoutesDistance.get(i),mRoutesDuration.get(i), mPoints[i]);

            }

// Drawing polyline in the Google Map for the i-th route
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String alternatives = "alternatives=true&";
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = alternatives + str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&alternatives=true"+ "&key=AIzaSyA0SLv8bTvT5m8RtWDSIisU8wREQr0UMxI";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        Log.e("Direction API URL: ",url);
        return url;
//        return "https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&alternatives=true&key=AIzaSyA0SLv8bTvT5m8RtWDSIisU8wREQr0UMxI";
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private void parseForDistance (JSONObject jObject){
        JSONArray jRoutes;

        try{
            jRoutes = jObject.getJSONArray("routes");
            for(int i=0;i<jRoutes.length();i++){
//                mRoutesDistance.add(Integer.parseInt((JSONObject)(JSONObject)((JSONObject)((JSONObject)((JSONObject) jRoutes.get(i)).get("legs")).get("0")).get("distance")).get("value")).);
                mRoutesDistance.add(Integer.parseInt(((JSONObject)((JSONObject)((JSONArray)((JSONObject)jRoutes.get(i)).get("legs")).get(0)).get("distance")).get("value").toString()));
                mRoutesDuration.add(Integer.parseInt(((JSONObject)((JSONObject)((JSONArray)((JSONObject)jRoutes.get(i)).get("legs")).get(0)).get("duration")).get("value").toString()));
            }
        }catch (Exception ex){
            Log.e("Exception in parseForDistance: ", ex.getMessage());
        }
    }
}
