package com.example.code19india.Navigation.ui.home;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;

    public MyItem(LatLng mPosition, String mTitle, String mSnicppet) {
        this.mPosition = mPosition;
        this.mTitle = mTitle;
        this.mSnicppet = mSnicppet;
    }

    private String mTitle;
    private String mSnicppet;


    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnicppet;
    }
}
