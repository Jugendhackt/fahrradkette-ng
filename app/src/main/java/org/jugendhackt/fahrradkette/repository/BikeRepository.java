package org.jugendhackt.fahrradkette.repository;

import org.jugendhackt.fahrradkette.model.Bike;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BikeRepository {

    private FahrradketteService fahrradketteService;
    private static BikeRepository bikeRepository;

    private BikeRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FahrradketteService.HTTPS_API_FAHRRADKETTE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fahrradketteService = retrofit.create(FahrradketteService.class);
    }

    public synchronized static BikeRepository getInstance() {
        if (bikeRepository == null) {
            bikeRepository = new BikeRepository();
        }
        return bikeRepository;
    }


    public LiveData<List<Bike>> getBoundingBikes(double latNorth, double lonWest, double latSouth, double lonEast) {
        final MutableLiveData<List<Bike>> data = new MutableLiveData<>();

        fahrradketteService.getBoundingBikes(/*latNorth, lonWest, latSouth, lonEast*/).enqueue(new Callback<List<Bike>>() {
            @Override
            public void onResponse(Call<List<Bike>> call, Response<List<Bike>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Bike>> call, Throwable t) {

            }
        });
        return data;
    }
}
