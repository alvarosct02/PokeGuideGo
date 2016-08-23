package com.github.alvarosct02.pokeguidego.retrofit;

import com.github.alvarosct02.pokeguidego.models.SubmissionBody;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {

    @GET(Urls.SUBMISSIONS)
    Call<SubmissionBody> getSubmissions(@Query("deviceId") String deviceId,
                                        @Query("minLatitude") double minLatitude,
                                        @Query("maxLatitude") double maxLatitude,
                                        @Query("minLongitude") double minLongitude,
                                        @Query("maxLongitude") double maxLongitude,
                                        @Query("pokemonId") int pokemonId);

}
