package com.example.code19india.Navigation.ui;

public class LatLngValue {
    public LatLngValue(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    String latitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    String longitude;

}
