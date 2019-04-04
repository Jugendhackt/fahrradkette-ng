package org.jugendhackt.fahrradkette.view.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapView;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.tangram.SceneUpdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.jugendhackt.fahrradkette.Fahrradkette;
import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.R;
import org.jugendhackt.fahrradkette.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


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
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                List<SceneUpdate> updates = new ArrayList<>();
                updates.add(new SceneUpdate("global.sdk_api_key", getString(R.string.mapzen_api_key)));

                map.getMapController().loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);
                map.setMyLocationEnabled(true);
            }
        });

        initMap();

        final MapViewModel viewModel = ViewModelProviders.of(this).get(MapViewModel.class);

        observeViewModel(viewModel);

        //bikeMapAdapter = new BikeMapAdapter(map, viewModel);
        //map.setMapListener(new DelayedMapListener(bikeMapAdapter, 200));
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

    private void initMap() {

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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
