package com.example.schatrijk_app.Data;

import android.location.Location;

import java.io.Serializable;

public class LocationBounds implements Serializable {
    private int radiusInMeters;
    private Location centerLocation;

    public LocationBounds(int radiusInMeters, Location centerLocation) {
        this.radiusInMeters = radiusInMeters;
        this.centerLocation = centerLocation;
    }

    public boolean checkInsideBounds(Location location) {
        float distanceInMeters = centerLocation.distanceTo(location);
        return distanceInMeters <= radiusInMeters;
    }

    public Location getCenterLocation() {
        return centerLocation;
    }
}
