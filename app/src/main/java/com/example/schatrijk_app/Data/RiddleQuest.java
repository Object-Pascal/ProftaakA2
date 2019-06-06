package com.example.schatrijk_app.Data;

import android.location.Location;

public class RiddleQuest extends Quest {
    private String riddle;
    private LocationBounds locationBounds;

    public RiddleQuest(String riddle, int questId, LocationBounds bounds) {
        super(questId);
        this.riddle = riddle;
        this.locationBounds = bounds;
    }

    public boolean verify(Object... params) throws TypeNotPresentException {
        if (!params[0].getClass().equals(Location.class)) {
            throw new TypeNotPresentException(params[0].getClass().getName(), new Throwable("Invalid location type passed"));
        }
        else {
            if (locationBounds.checkInsideBounds((Location)params[0])) {
                completed = true;
            }
        }
        return completed;
    }

    public String getRiddle() {
        return riddle;
    }

    public LocationBounds getLocationBounds() {
        return locationBounds;
    }
}
