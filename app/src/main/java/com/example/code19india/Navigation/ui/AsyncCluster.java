package com.example.code19india.Navigation.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.code19india.Navigation.ui.home.MyItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;

public class AsyncCluster extends AsyncTask<Void,Void,Void> {
    Activity activity;
    ClusterManager<MyItem> clusterManager;
    GoogleMap googleMap;
    public AsyncCluster(Activity activity, ClusterManager<MyItem> clusterManager, GoogleMap googleMap)
    {
this.activity=activity;
this.clusterManager=clusterManager;
this.googleMap=googleMap;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clusterManager.setRenderer(new MarkerClusterRenderer(activity,googleMap,clusterManager));
                clusterManager.setAnimation(false);
            }
        });

        return null;
    }
}
