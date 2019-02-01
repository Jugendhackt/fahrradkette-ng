package org.jugendhackt.fahrradkette;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;

import java.util.ArrayList;
import java.util.List;

public class BikeDataProvider {

    private List<IGeoPoint> pointsCache = new ArrayList<>();

    public void onUpdateViewPort(double latNorth, double lonWest, double latSouth, double lonEast) {
        pointsCache.add(new LabelledGeoPoint(latNorth, lonWest
                , "North West"));
        pointsCache.add(new LabelledGeoPoint(latSouth, lonEast
                , "North West"));
    }

    public List<IGeoPoint> getPointsCache() {
        return pointsCache;
    }
}
