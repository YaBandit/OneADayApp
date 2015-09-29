package com.evolve.dylan.oneaday.serverHandling;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dylan on 29/09/2015.
 */
public interface GetPhotoApi {

    @GET("/api/photo")
    void getPhotoApi (Callback<PictureData> callback);

}
