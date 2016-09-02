package com.github.alvarosct02.pokeguidego.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.alvarosct02.pokeguidego.R;
import com.github.alvarosct02.pokeguidego.models.Submission;
import com.github.alvarosct02.pokeguidego.models.SubmissionBody;
import com.github.alvarosct02.pokeguidego.retrofit.RequestManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FloatingActionButton fab_location, fab_search;
    private View v_progress;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab_location = (FloatingActionButton) findViewById(R.id.fab_location);
        fab_search = (FloatingActionButton) findViewById(R.id.fab_search);
        v_progress = findViewById(R.id.v_progress);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TK Pokemap");
        toolbar.setTitleTextColor(Color.WHITE);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    private void setLoading(boolean loading) {
        fab_location.setEnabled(!loading);
        v_progress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void findPokemons(LatLng min, LatLng max) {

        Call<SubmissionBody> getPokemons =
                RequestManager.getWebServices().getSubmissions(
                        "8833e1905bf711e6bdb23f74a6aceeb0",
                        min.latitude,
                        max.latitude,
                        min.longitude,
                        max.longitude,
                        0);

        setLoading(true);
        getPokemons.enqueue(new Callback<SubmissionBody>() {
            @Override
            public void onResponse(Call<SubmissionBody> call, Response<SubmissionBody> response) {

                setLoading(false);
                if (mMap == null) return;

                mMap.clear();

                List<Submission> pokemons = response.body().getVerifiedResults();

                for (Submission pokemon : pokemons) {
                    int icon = getResources().getIdentifier(String.format("drawable/pokemon_%d", pokemon.getPokemonId()), null, getPackageName());
                    mMap.addMarker(new MarkerOptions()
                            .position(pokemon.getLatLng())
                            .title(pokemon.getLabel())
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory.fromResource(icon)));
                }


//                Toast.makeText(MainActivity.this, String.format(Locale.US, "%d pokemons found", pokemons.size()), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<SubmissionBody> call, Throwable t) {
                setLoading(false);
                Toast.makeText(MainActivity.this, "Something went wrong, Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.123563, -77.029412),17));


            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    return false;
                }
            });

            fab_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMyLocation();
                }
            });

            fab_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSearch();
                }
            });
        }
    }

    public void onClickSearch() {
        if (mMap != null) {
            LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
            findPokemons(bounds.southwest, bounds.northeast);
        }
    }

    public void onClickMyLocation() {
        if (mMap != null) {
            View myLocationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent())
                    .findViewById(Integer.parseInt("2"));

            myLocationButton.performClick();
        }

    }


}
