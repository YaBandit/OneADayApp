package com.evolve.dylan.oneaday.serverHandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by dylan on 29/09/2015.
 */
public class GetPhotoRestAdapter {

    protected RestAdapter restAdapter;
    protected GetPhotoApi api;
    private static final String apiURL = "http://192.168.1.105:9001";

    public GetPhotoRestAdapter() {
        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(apiURL)
                .setErrorHandler(new PictureErrorHandler())
                .setConverter(new GsonConverter(gson))
                .build();
        api = restAdapter.create(GetPhotoApi.class);
    }

    public void getPhoto(Callback<PictureData> callback) {
        api.getPhotoApi(callback);
    }
}
