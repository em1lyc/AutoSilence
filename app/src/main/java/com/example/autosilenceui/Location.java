package com.example.autosilenceui;

import android.util.Log;

public class Location {
    private String name;
    private String location;
    public Location(String n, String l){
        name = n;
        location = l;
    }
    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public void setName(String n) {
        name = n;
    }
    public void setLocation(String l) {
        location = l;
    }
    public String toString() {
        return "" + name + location;
    }
}
