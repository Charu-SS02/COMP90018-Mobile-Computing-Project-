package com.example.findcoffee.model;

import android.location.Location;

import com.example.findcoffee.ui.arView.helper.LocationHelper;

/**
 * Created by: Xixiang Wu
 * Date:       9/11/20.
 * Email:      xixiangw@student.unimelb.edu.au
 */
public class User {

    private Location location;
    private double distanceToTarget;

    private static User instance = new User();

    public static User getInstance() {
        return instance;
    }

    public User() {}

    public void updateLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
