package org.jugendhackt.fahrradkette.view.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.BitmapMarker;
import com.mapzen.android.graphics.model.BitmapMarkerFactory;
import com.mapzen.android.graphics.model.BitmapMarkerOptions;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.jugendhackt.fahrradkette.Fahrradkette;
import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.R;
import org.jugendhackt.fahrradkette.view.adapter.BikeMapAdapter;
import org.jugendhackt.fahrradkette.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapzenMap map;
    private BikeMapAdapter bikeMapAdapter;
    private MapViewModel mapViewModel;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean hasDeniedLocationPermission = false;

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


        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(new MapStyle("bubble-wrap/bubble-wrap-style.yaml"), this);



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
        map.setMyLocationEnabled(enable);
        if(enable) {
            //center map

        }
    }

    @Override public void onMapReady(MapzenMap map) {
        this.map = map;
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", getString(R.string.mapzen_api_key)));
        //map.getMapController().loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);
        map.getMapController().updateSceneAsync(updates);

        enableLocation(isLocationPermissionGranted());

        //map.addMarker(new Marker(11, 52));
        //Marker m = new Marker(34, 34);
        //bikeMapAdapter = new BikeMapAdapter(map, mapViewModel);
        //map.setPanResponder(bikeMapAdapter);
    }

    private void observeViewModel(MapViewModel viewModel) {
        viewModel.getMapBikesObservable().observe(this, new Observer<List<Bike>>() {
            @Override
            public void onChanged(List<Bike> bikes) {
                Log.d(Fahrradkette.TAG, "OnChanged");
                //if (bikeMapAdapter != null) {
                //    bikeMapAdapter.setBikes(bikes);
                //}
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
                    map.setMyLocationEnabled(true);
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
        if(map != null) {
            enableLocation(isLocationPermissionGranted());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(map != null) {
            enableLocation(false);
        }
    }
}
