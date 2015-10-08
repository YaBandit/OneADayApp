package com.evolve.dylan.oneaday.serverHandling;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by dylan on 29/09/2015.
 */
public class GetPhotoRestAdapter {

    protected RestAdapter restAdapter;
    protected GetPhotoApi api;
    private static final String apiURL = "http://192.168.1.105:9001";

    public GetPhotoRestAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(apiURL)
                .build();
        api = restAdapter.create(GetPhotoApi.class);
    }

    public void getPhoto(Callback<PictureData> callback) {
        api.getPhotoApi(callback);
    }
}
