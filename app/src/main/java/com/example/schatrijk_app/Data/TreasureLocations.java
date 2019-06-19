package com.example.schatrijk_app.Data;

import android.location.Location;

import static android.location.LocationManager.GPS_PROVIDER;

public class TreasureLocations {
    public static LocationBounds[] getTreasureLocations() {
        Location firstLocationHogeschool = new Location(GPS_PROVIDER);
        firstLocationHogeschool.setLatitude(51.584225);
        firstLocationHogeschool.setLongitude(4.796802);

        LocationBounds[] locations = {
            new LocationBounds(50, firstLocationHogeschool)
        };

        return locations;
    }
}
