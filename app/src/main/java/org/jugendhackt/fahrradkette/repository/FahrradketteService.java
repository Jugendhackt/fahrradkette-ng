package org.jugendhackt.fahrradkette.repository;

import org.jugendhackt.fahrradkette.model.Bike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface FahrradketteService {

    String HTTPS_API_FAHRRADKETTE_URL = "https://devtarf.ddns.net/api/";

    @GET("bikes")
    Call<List<Bike>> getBoundingBikes(/*double latNorth, double lonWest, double latSouth, double lonEast*/);
}
