package com.example.code19india.Navigation.ui.tools;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
MapView mMapView;
GoogleMap googleMap;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        mMapView = (MapView) root.findViewById(R.id.mapView);
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
Toast.makeText(getContext(),"Click the marker to place your order",Toast.LENGTH_LONG).show();
                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);
               googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                   @Override
                   public boolean onMarkerClick(Marker marker) {
                      Log.e("tag",marker.getTag().toString());
                      if(!marker.getTag().toString().isEmpty()) {
                          showShopAlert(marker);
                      }
                        return false;
                   }
               });
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

                getShop(googleMap);

            }
        });

        return root;
    }
    public void showShopAlert(Marker marker)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.shop_items, null);
        dialogBuilder.setView(dialogView);
        EditText mobileno=dialogView.findViewById(R.id.mobileno);
        EditText items=dialogView.findViewById(R.id.items);
        Button bt=dialogView.findViewById(R.id.submit);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag",marker.getTag().toString());
                if(mobileno.getText().toString().isEmpty() || items.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Field is empty",Toast.LENGTH_LONG).show();
                }
                else {
                    sendToShop(marker.getTag().toString(), mobileno.getText().toString(), items.getText().toString());
                }
                }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void sendToShop(String toString, String toString1, String toString2) {
    String url="http://192.168.43.83/php//shop_items.php";
    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
Toast.makeText(getContext(),"Request send to the respected shop",Toast.LENGTH_LONG).show();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String,String> params=new HashMap<String,String>();
            params.put("id",toString);
            params.put("mobileno",toString1);
            params.put("items",toString2);
            return params;
        }
    };
    singleton.getInstance(getActivity()).addtoRequestqueue(stringRequest);
    }

    private void getShop(GoogleMap googleMap) {
String url="http://192.168.43.83/php//groceries.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Log.e("response",response);
                JSONArray jsonArray= null;

                try {
                    jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String latitude=jsonObject.getString("latitude");
                    String longitude=jsonObject.getString("longitude");
                    String id=jsonObject.getString("id");
                    Marker marker=googleMap.addMarker(new MarkerOptions().position(new LatLng(Double
                    .parseDouble(latitude),Double.parseDouble(longitude))).title("Grocery shop"));
                    marker.setTag(id);
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