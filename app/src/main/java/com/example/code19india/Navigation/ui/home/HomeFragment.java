package com.example.code19india.Navigation.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.amalbit.trail.Route;
import com.amalbit.trail.RouteOverlayView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.MainActivity;
import com.example.code19india.Navigation.ui.Async;
import com.example.code19india.Navigation.ui.AsyncCluster;
import com.example.code19india.Navigation.ui.LatLngValue;
import com.example.code19india.Navigation.ui.MarkerClusterRenderer;
import com.example.code19india.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
MapView mMapView;
    public static ArrayList<Marker> markerlist;
GoogleMap googleMap;
EditText search;
Button bt;
ClusterManager<MyItem> clusterManager;
Button route;
Marker markervalue;
public static ArrayList a,b;
View view;
ProgressDialog progressDialog;
public static String affected;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        view=rootView;
        route=rootView.findViewById(R.id.routesug);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                progressDialog.dismiss();
                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);
                final int[] count = {0};
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
if(count[0] ==0) {
    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
    mMap.moveCamera(center);
    mMap.animateCamera(zoom);
count[0]++;
}
                    }
                });

                getCoronoData(googleMap);

                     }
        });

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    search=view.findViewById(R.id.search);
    bt=view.findViewById(R.id.searchbt);
    bt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             progressDialog=new ProgressDialog(getContext());
            progressDialog.setTitle("Please Wait");
            progressDialog.show();
            if(search.getText().toString().isEmpty())
            {
                Toast.makeText(getContext(),"Please enter some text",Toast.LENGTH_LONG).show();
            }
            else
            {
                String value=search.getText().toString().trim();
                affectedPersons(value);
                LatLng latLng=getLocationFromAddress(getContext(),value);

                if(markervalue!=null)
                {
                    markervalue.remove();
                }
               if(latLng!=null && affected!=null) {
                   markervalue = googleMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title(value).snippet("Number of people affected "+affected));
           markervalue.showInfoWindow();
                   CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                   CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
                   googleMap.moveCamera(center);
                   googleMap.animateCamera(zoom);
               }
            }
        }
    });

    }
    public void getCoronoData(final GoogleMap googleMap)
    {

         a = new ArrayList<>();
         b=new ArrayList<>();
    markerlist=new ArrayList<>();
        clusterManager=new ClusterManager(getContext(),googleMap);
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
        getList();
        //Log.e("count",""+Async.count);

    }
public void getList()
{
    if(Async.arrayList!=null) {
        for (int i = 0; i < Async.arrayList.size(); i++) {

            Log.e("latlng", "" + Async.arrayList.get(0).latitude);
            if (googleMap != null) {
                drawMarkerWithCircle(Async.arrayList.get(i), googleMap);
                Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(String.valueOf(Async.arrayList.get(i).latitude)), Double.parseDouble(String.valueOf(Async.arrayList.get(i).longitude)))));
                markerlist.add(marker);
                marker.setVisible(false);
                MyItem myItem = new MyItem(new LatLng(Double.parseDouble(String.valueOf(Async.arrayList.get(i).latitude)), Double.parseDouble(String.valueOf(Async.arrayList.get(i).longitude))), "", "Corono affected places");
                clusterManager.addItem(myItem);

            }
        }
        AsyncCluster asyncCluster = new AsyncCluster(getActivity(), clusterManager, googleMap);
        asyncCluster.execute();


    }
    else
    {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getList();
            }
        },5000);
    }

}
    private void getSuggestion() {
    }

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void drawMarkerWithCircle(LatLng position,GoogleMap mGoogleMap){
        double radiusInMeters = 100.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mGoogleMap.addCircle(circleOptions);


    }
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }
progressDialog.dismiss();
        return p1;
    }
    public void affectedPersons(String search)
    {
        String url="https://api.covid19india.org/state_district_wise.json";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Log.e("response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    for(int i=0;i<jsonObject.length();i++)
                    {
                        String state=jsonObject.getString("Tamil Nadu");
                        Log.e("state",state);
                        JSONObject jsonObject1=new JSONObject(state);
                        for(int j=0;j<jsonObject1.length();j++)
                        {

                            String district=jsonObject1.getString("districtData");
                            Log.e("district",district);
                            JSONObject jsonObject2=new JSONObject(district);

//                 Log.e("search",search);
                            String city=jsonObject2.getString(search);
                            JSONObject jsonObject3=new JSONObject(city);
                             affected=jsonObject3.getString("confirmed");
                        Log.e("affected",affected);


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.getInstance(getActivity()).addtoRequestqueue(stringRequest);
    }

}