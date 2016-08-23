package com.github.alvarosct02.pokeguidego.models;

/**
 * Created by Alvaro on 8/21/2016.
 */
public class Submission {
    private static final String POKE_RADAR_PREDICTION = "13661365";

    private int created;
    private float latitude;
    private float longitude;
    private int pokemonId;
    private String userId;

    private int downvotes;
    private int upvotes;
    private String trainerName;
    private String deviceId;

    public int getCreated() {
        return created;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
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
}
