package com.example.code19india.Navigation.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.code19india.Navigation.ui.home.MyItem;
import com.example.code19india.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class MarkerClusterRenderer extends DefaultClusterRenderer<MyItem> {
Activity activity;
GoogleMap map;
    public MarkerClusterRenderer(Activity activity, GoogleMap map,
                                 ClusterManager<MyItem> clusterManager) {
        super(activity, map, clusterManager);
    this.activity=activity;
    this.map=map;
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        // use this to make your change to the marker option
        // for the marker before it gets render on the map
        int height = 40;
        int width = 40;

        Drawable drawable = activity.getResources().getDrawable(R.drawable.circle);
        Bitmap bd = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bd);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        Bitmap smallMarker = Bitmap.createScaledBitmap(bd, width, height, false);

     markerOptions.icon(BitmapDescriptorFactory.
                fromBitmap(smallMarker)).title("Red Marker denotes").snippet("Corono Affected Places");

    }
}