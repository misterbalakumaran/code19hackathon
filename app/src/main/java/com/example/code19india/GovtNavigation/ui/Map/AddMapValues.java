package com.example.code19india.GovtNavigation.ui.Map;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMapValues extends Fragment {
    GoogleMap mMap;
    Button csv;
    ArrayList latitudelist;
    ArrayList longitudelist;
    Button submit;
    String[] data1;
    EditText lat;
    EditText lang;
  public static double latitude;
    public static double longitude;
    Marker marker=null;
    LatLng startlatlng;
    LatLng endlatlng;
    Button loc;
    ArrayList<String>[] arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_map_values, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      latitudelist=new ArrayList<String>();
      longitudelist=new ArrayList<String>();
      lat=view.findViewById(R.id.lat);
      lang=view.findViewById(R.id.lang);
        csv = view.findViewById(R.id.csv);
        submit=view.findViewById(R.id.submit);
       loc=view.findViewById(R.id.loc);
       loc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openMap();
           }
       });
        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile(101);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!lat.getText().toString().isEmpty()) {

                        addMapValue(lat.getText().toString(), lang.getText().toString());

                }
                else if(!latitudelist.isEmpty() && !longitudelist.isEmpty())
                {
                    for (int i = 0; i < latitudelist.size(); i++) {
                        addMapValue(latitudelist.get(i).toString(), longitudelist.get(i).toString());
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"please select latitude and longitude",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
private void addMapValue(final String latitude, final String longitude)
{
    String url="http://192.168.43.83/php//map_values.php";
    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if(!response.isEmpty())
            {
                Toast.makeText(getContext(),"Map updated Successfully",Toast.LENGTH_LONG).show();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String,String> params=new HashMap<String,String>();
            params.put("latitude",latitude);
            params.put("longitude",longitude);
            return params;
        }
    };
    singleton.getInstance(getContext()).addtoRequestqueue(stringRequest);
}
    private void openFile(int CODE) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        startActivityForResult(Intent.createChooser(intent, "Open CSV"), 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();

        try {
            InputStream inputStream=getContext().getContentResolver().openInputStream(uri);

            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(isr);
            String row=null;
             int k=-1,m=-1;
            while ((row = bufferedReader.readLine()) != null) {

               String[] data1 = row.split(",");
              for(int i=0;i<data1.length;i++)
              {
                  if(data1[i].contains("latitude"))
                  {
                      k=i;
                      Log.e("k",""+k);
                  }
                  else if(data1[i].contains("longitude"))
                  {
                      m=i;
                      Log.e("m",""+m);
                  }
                  if(k==i)
                  {
                      latitudelist.add(data1[i]);
                  }
                  else if(i==m)
                  {
                      longitudelist.add(data1[i]);
                  }
              }
            }
Toast.makeText(getContext(),"Successfully Uploaded",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMap()
    {

        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_map);
        dialog.show();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
        Button bt=dialog.findViewById(R.id.submit);
        MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
        MapsInitializer.initialize(getActivity());

        mMapView = (MapView) dialog.findViewById(R.id.mapView);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();// needed to get the map to display immediately
        MapView finalMMapView = mMapView;
        MapView finalMMapView1 = mMapView;
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap=googleMap;
                googleMap.setMyLocationEnabled(true);
                int height = 40;
                int width = 40;


                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                        latitude=latLng.latitude;
                        longitude=latLng.longitude;
mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)));


                    }
                });


            }
        });
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        lat.setText(""+latitude);
    lang.setText(""+longitude);
    dialog.dismiss();
    }
});

    }

}
