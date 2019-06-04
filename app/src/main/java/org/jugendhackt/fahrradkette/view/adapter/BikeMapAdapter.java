package org.jugendhackt.fahrradkette.view.adapter;

import android.graphics.PointF;
import android.util.Log;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapChangeListener;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;
import com.mapzen.tangram.MarkerPickListener;
import com.mapzen.tangram.MarkerPickResult;

import org.jugendhackt.fahrradkette.Fahrradkette;
import org.jugendhackt.fahrradkette.R;
import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.viewmodel.MapViewModel;

import java.util.HashMap;
import java.util.List;


public class BikeMapAdapter implements MapChangeListener {

    private MapViewModel viewModel;
    private MapController map;
    private HashMap<Integer, Marker> bikeCache;
    String pointStyle = "{ style: 'points', color: 'white', size: [60px, 60px], order: 2000, collide: true , interactive: true }"; //move to values xml

    public BikeMapAdapter(MapController map, MapViewModel viewModel) {
        this.map = map;
        this.viewModel = viewModel;
        bikeCache = new HashMap<>();
    }

    public void setBikes(List<Bike> bikes) {
        Log.d(Fahrradkette.TAG, "Reload bikes");

        for (Bike bike : bikes) {
            if(!bikeCache.containsKey(bike.id)) {
                Marker bikeMarker = map.addMarker();
                bikeMarker.setPoint(new LngLat(bike.longitude, bike.latitude));
                bikeMarker.setStylingFromString(pointStyle);
                bikeMarker.setUserData(bike);
                Log.d(Fahrradkette.TAG, String.valueOf(bikeMarker.setDrawable(R.drawable.ic_bike)));
                bikeCache.put(bike.id, bikeMarker);
            }
        }
        Log.d(Fahrradkette.TAG, String.format("%d", bikeCache.size()));
    }

    private void onUpdateViewPort() {
        LngLat topleft = map.screenPositionToLngLat(new PointF(0, 0));
        LngLat bottomright = map.screenPositionToLngLat(
                new PointF(map.getGLViewHolder().getView().getWidth(), map.getGLViewHolder().getView().getHeight()));

        viewModel.loadBikes(
                topleft.latitude,
                topleft.longitude,
                bottomright.latitude,
                bottomright.longitude);
    }

    @Override
    public void onViewComplete() {

    }

    @Override
    public void onRegionWillChange(boolean animated) {

    }

    @Override
    public void onRegionIsChanging() {

    }

    @Override
    public void onRegionDidChange(boolean animated) {
        onUpdateViewPort();
    }
}
