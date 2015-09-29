package com.evolve.dylan.oneaday.serverHandling;

import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dylan on 29/09/2015.
 */
public class PictureErrorHandler implements ErrorHandler{

    protected final String TAG = getClass().getSimpleName();

    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == 401) {
            Log.e(TAG, "Error:", cause);
        }
        return cause;
    }
}
