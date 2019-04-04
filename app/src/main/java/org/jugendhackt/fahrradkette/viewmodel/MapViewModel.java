package org.jugendhackt.fahrradkette.viewmodel;

import android.app.Application;
import android.util.Log;

import org.jugendhackt.fahrradkette.Fahrradkette;
import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.repository.BikeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MapViewModel extends AndroidViewModel {

    private MutableLiveData<List<Bike>> mapBikesObservable;

    public MapViewModel(@NonNull Application application) {
        super(application);
        //mapBikesObservable = BikeRepository.getInstance().getBoundingBikes(ma52 , 10 ,51 ,11);
        mapBikesObservable = new MutableLiveData<>();
        loadBikes( 54.0, 51.0, 0.0, 11.0);
    }

    public LiveData<List<Bike>> getMapBikesObservable() {
        return mapBikesObservable;
    }

    public void loadBikes(double latNorth, double lonWest, double latSouth, double lonEast) {
        //mapBikesObservable = BikeRepository.getInstance().getBoundingBikes(latNorth, lonWest, latSouth, lonEast);
        BikeRepository.getInstance().getBoundingBikes(mapBikesObservable, latNorth, lonWest, latSouth, lonEast);
    }
}
