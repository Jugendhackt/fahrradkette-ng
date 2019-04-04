package org.jugendhackt.fahrradkette.repository;

import org.jugendhackt.fahrradkette.model.Bike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface FahrradketteService {

    String HTTPS_API_FAHRRADKETTE_URL = "https://devtarf.ddns.net/api/";

    @GET("bikes/bounding")
    Call<List<Bike>> getBoundingBikes(@Query("lat_north") double latNorth,
                                      @Query("lon_west") double lonWest,
                                      @Query("lat_south") double latSouth,
                                      @Query("lon_east") double lonEast);
}
