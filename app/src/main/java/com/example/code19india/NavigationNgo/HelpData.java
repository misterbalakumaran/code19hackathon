package com.example.code19india.NavigationNgo;

public class HelpData {
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    String place;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeofhelp() {
        return typeofhelp;
    }

    public void setTypeofhelp(String typeofhelp) {
        this.typeofhelp = typeofhelp;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    String latitude;
    String longitude;
    String mobileno;

    String description;
    String typeofhelp;
    String count;

    public HelpData(String latitude, String longitude, String mobileno, String description, String typeofhelp, String count, String id,String place) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.mobileno = mobileno;
        this.description = description;
        this.typeofhelp = typeofhelp;
        this.count = count;
    this.place=place;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
}
