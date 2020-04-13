package com.example.code19india.Navigation.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.code19india.GovtNavigation.ui.Map.singleton;
import com.example.code19india.Navigation.NavigationDrawer;
import com.example.code19india.Navigation.ui.home.MyItem;
import com.example.code19india.NavigationNgo.NavigationNgo;
import com.example.code19india.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Async extends AsyncTask<Void,Void,Void> {
    public static int count=0;
Context context;
public static ArrayList<LatLng> arrayList;
    public Async(NavigationDrawer navigationDrawer) {
    this.context=navigationDrawer;
    }

    public Async(NavigationNgo navigationNgo) {
    this.context=navigationNgo;
    }



    @Override
    protected Void doInBackground(Void... voids) {
        getCoronoData();
        return null;
    }



    public void getCoronoData()
    {
        String url="http://192.168.43.83/php//map_retrieve.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //    Log.e("response",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    count=jsonArray.length();
                    arrayList=new ArrayList<LatLng>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String latitude=jsonObject.getString("latitude");
                        String longitude=jsonObject.getString("longitude");
                        if(!isDouble(latitude) && !isDouble(longitude))
                        {
                            continue;
                        }
                        arrayList.add(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)));
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        singleton.getInstance(context).addtoRequestqueue(stringRequest);


    }
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
