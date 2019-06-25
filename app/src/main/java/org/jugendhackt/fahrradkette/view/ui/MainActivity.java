package org.jugendhackt.fahrradkette.view.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.MarkerPickListener;
import com.mapzen.tangram.MarkerPickResult;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import org.jugendhackt.fahrradkette.Fahrradkette;
import org.jugendhackt.fahrradkette.R;
import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.view.adapter.BikeMapAdapter;
import org.jugendhackt.fahrradkette.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    private MapView mapView;
    private MapController map;
    private BikeMapAdapter bikeMapAdapter;
    private MapViewModel mapViewModel;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean hasDeniedLocationPermission = false;
    private MarkerBottomSheet markerBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        markerBottomSheet = new MarkerBottomSheet(findViewById(R.id.bottom_sheet_main));

        mapView = findViewById(R.id.map);
        mapView.getMapAsync(this);

        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        observeViewModel(mapViewModel);
    }

    private boolean isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) && !hasDeniedLocationPermission) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            return false;
        }
        else {
            return true;
        }
    }

    private void enableLocation(boolean enable) {
        //map.setMyLocationEnabled(enable);
        if(enable) {
            //center map

        }
    }

    private void observeViewModel(MapViewModel viewModel) {
        viewModel.getMapBikesObservable().observe(this, new Observer<List<Bike>>() {
            @Override
            public void onChanged(List<Bike> bikes) {
                Log.d(Fahrradkette.TAG, "OnChanged");
                if (bikeMapAdapter != null) {
                    bikeMapAdapter.setBikes(bikes);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    //map.setMyLocationEnabled(true);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // map.setMyLocationEnabled(false);
                    hasDeniedLocationPermission = true;
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        /*if(map != null) {
            enableLocation(isLocationPermissionGranted());
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        /*
        if(map != null) {
            enableLocation(false);
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@Nullable MapController mapController) {
        this.map = mapController;
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", getString(R.string.mapzen_api_key)));
        mapController.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);
        enableLocation(isLocationPermissionGranted());

        bikeMapAdapter = new BikeMapAdapter(map, mapViewModel);
        map.setMapChangeListener(bikeMapAdapter);
        map.setMarkerPickListener(markerBottomSheet);
        map.getTouchInput().setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(float x, float y) {
                map.pickMarker(x, y);
                return true;
            }
        });
    }
}
