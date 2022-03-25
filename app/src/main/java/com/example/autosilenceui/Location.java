package com.example.autosilenceui;

import android.util.Log;

public class Location {
    private String name;
    private String location;
    private int latitude;
    private int longitude;
    public Location(String n, String l, int lat, int lon){
        name = n;
        location = l;
        latitude = lat;
        longitude = lon;
    }
    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public int getLatitude() { return latitude;}
    public int getLongitude() {return longitude;}
    public void setName(String n) {
        name = n;
    }
    public void setLocation(String l) {
        location = l;
    }
    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
    public String toString() {
        return "" + name + location;
    }
}
