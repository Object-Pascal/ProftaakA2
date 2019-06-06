package com.example.schatrijk_app.Data;

import android.location.Location;

public class LocationBounds {
    private int radiusInMeters;
    private Location centerLocation;

    public LocationBounds(int radiusInMeters, Location centerLocation) {
        this.radiusInMeters = radiusInMeters;
        this.centerLocation = centerLocation;
    }

    public boolean checkInsideBounds(Location location)
    {
        float distanceInMeters = centerLocation.distanceTo(location);
        return distanceInMeters <= radiusInMeters;
    }
}
