package org.jugendhackt.fahrradkette.view.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import org.jugendhackt.fahrradkette.Fahrradkette;
import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.viewmodel.MapViewModel;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.MapAdapter;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.util.ArrayList;
import java.util.List;

public class BikeMapAdapter extends MapAdapter {

    private List<IGeoPoint> pointsCache = new ArrayList<>();
    private MapView map;
    private MapViewModel viewModel;

    public BikeMapAdapter(MapView map, MapViewModel viewModel) {
        this.map = map;
        this.viewModel = viewModel;

        SimplePointTheme pt = new SimplePointTheme(pointsCache, true);

        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#0000ff"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                .setRadius(7).setIsClickable(true).setCellSize(15).setTextStyle(textStyle);

        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

// onClick callback
        sfpo.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
            @Override
            public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
                // stuff
            }
        });

// add overlay
        map.getOverlays().add(sfpo);
    }

    public void setBikes(List<Bike> bikes) {
        //pointsCache.clear();
        Log.d(Fahrradkette.TAG, "Reload bikes");
        for (Bike bike : bikes) {
            LabelledGeoPoint lgp = new LabelledGeoPoint(bike.latitude, bike.longitude, bike.notes);
            pointsCache.add(lgp);
        }
        Log.d(Fahrradkette.TAG, String.format("%d", pointsCache.size()));
       // map.invalidate();
    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        onUpdateViewPort();

        return super.onScroll(event);
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        onUpdateViewPort();

        return super.onZoom(event);
    }

    private void onUpdateViewPort() {
        viewModel.loadBikes(
                map.getBoundingBox().getLatNorth(),
                map.getBoundingBox().getLonWest(),
                map.getBoundingBox().getLatSouth(),
                map.getBoundingBox().getLonEast());
    }
}
