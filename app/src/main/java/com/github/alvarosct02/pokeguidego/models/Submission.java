package com.github.alvarosct02.pokeguidego.models;

import com.github.alvarosct02.pokeguidego.utils.UtilMethods;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alvaro on 8/21/2016.
 */
public class Submission {
    private static final String POKE_RADAR_PREDICTION = "13661365";

    private long created;
    private double latitude;
    private double longitude;
    private int pokemonId;
    private String userId;

    private int downvotes;
    private int upvotes;
    private String trainerName;
    private String deviceId;

    public long getCreated(){
        return created * 1000;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getPokemonId() {
        return pokemonId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isVerified(){
        return getUserId().equals(POKE_RADAR_PREDICTION);
    }

    public Calendar getEndTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getCreated());
        c.add(Calendar.MINUTE,15);
        return c;
    }

    public String getLabel(){
        return String.format("Until %s", UtilMethods.getDateString(getEndTime(), "HH:mm"));
    }

    public LatLng getLatLng(){
        return new LatLng(getLatitude(), getLongitude());
    }
}
