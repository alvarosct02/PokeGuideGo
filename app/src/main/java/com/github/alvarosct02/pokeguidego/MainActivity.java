package com.github.alvarosct02.pokeguidego;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.alvarosct02.pokeguidego.models.Submission;
import com.github.alvarosct02.pokeguidego.models.SubmissionBody;
import com.github.alvarosct02.pokeguidego.retrofit.RequestManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } catch (SecurityException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void findPokemons(double latitude, double longitude){
        Call<SubmissionBody> getPokemons =
                RequestManager.getWebServices().getSubmissions(
                        "8833e1905bf711e6bdb23f74a6aceeb0",
                        -12.0766305453658f,
                        -12.063348113228034f,
                        -77.09743738174437f,
                        -77.07692384719849f,
                        0);

        getPokemons.enqueue(new Callback<SubmissionBody>() {
            @Override
            public void onResponse(Call<SubmissionBody> call, Response<SubmissionBody> response) {

                List<Submission> pokemons = response.body().getVerifiedResults();
                Toast.makeText(MainActivity.this, String.format(Locale.US, "%d pokemons found", pokemons.size()), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<SubmissionBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude =location.getLatitude();
        double longitude = location.getLongitude();
        Toast.makeText(MainActivity.this, "HERE", Toast.LENGTH_SHORT).show();

//        findPokemons(latitude, longitude);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
