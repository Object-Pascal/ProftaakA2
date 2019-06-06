package com.example.schatrijk_app.Data;

import android.location.Location;

import static android.location.LocationManager.GPS_PROVIDER;

public class TreasureLocations {
    public static LocationBounds[] getTreasureLocations() {
        Location firstLocationHogeschool = new Location(GPS_PROVIDER);
        firstLocationHogeschool.setLatitude(51.584505);
        firstLocationHogeschool.setLongitude(4.797117);

        LocationBounds[] locations = {

        };

        return locations;
    }
}
