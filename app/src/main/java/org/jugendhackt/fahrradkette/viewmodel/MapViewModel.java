package org.jugendhackt.fahrradkette.viewmodel;

import android.app.Application;

import org.jugendhackt.fahrradkette.model.Bike;
import org.jugendhackt.fahrradkette.repository.BikeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MapViewModel extends AndroidViewModel {

    private final LiveData<List<Bike>> mapBikesObservable;

    public MapViewModel(@NonNull Application application) {
        super(application);

        mapBikesObservable = BikeRepository.getInstance().getBoundingBikes(52 , 10 ,51 ,11);
    }

    public LiveData<List<Bike>> getMapBikesObservable() {
        return mapBikesObservable;
    }
}
