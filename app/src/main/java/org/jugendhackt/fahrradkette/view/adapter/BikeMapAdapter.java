package org.jugendhackt.fahrradkette.view.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.tangram.TouchInput;

import org.jugendhackt.fahrradkette.Fahrradkette;
import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.List;

public class BikeMapAdapter implements TouchInput.PanResponder {

    private MapViewModel viewModel;
    private MapzenMap map;

    public BikeMapAdapter(MapzenMap map, MapViewModel viewModel) {
        this.map = map;
        this.viewModel = viewModel;


    }

    public void setBikes(List<Bike> bikes) {
        //pointsCache.clear();
        Log.d(Fahrradkette.TAG, "Reload bikes");
        for (Bike bike : bikes) {
            //LabelledGeoPoint lgp = new LabelledGeoPoint(bike.latitude, bike.longitude, bike.notes);
            //pointsCache.add(lgp);
        }
        //Log.d(Fahrradkette.TAG, String.format("%d", pointsCache.size()));
       // map.invalidate();
    }



    private void onUpdateViewPort() {
        //viewModel.loadBikes(
                //map.getBoundingBox().getLatNorth(),
                //map.getBoundingBox().getLonWest(),
                //map.getBoundingBox().getLatSouth(),
                //map.getBoundingBox().getLonEast());
    }

    @Override
    public boolean onPan(float startX, float startY, float endX, float endY) {
        return false;
    }

    @Override
    public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
        Log.d(Fahrradkette.TAG, String.format("%d - %d", posX, posY));
        return false;
    }
}
