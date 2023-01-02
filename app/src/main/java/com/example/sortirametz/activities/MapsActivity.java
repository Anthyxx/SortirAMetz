package com.example.sortirametz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.modeles.Site;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.sortirametz.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int ACCESS_LOCATION_REQUEST_CODE = 10001;

    double distance_parametered;

    private FusedLocationProviderClient fusedLocationProviderClient;
    Task<Location> locationTask;

    public DAOSite daoSite = new DAOSite();
    //ArrayList<Site> listSites = daoSite.getAllSites(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //fusedLocationProviderClient_old = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(getIntent().hasExtra("map_category") && getIntent().hasExtra("map_radius")){
            distance_parametered = Double. parseDouble(getIntent().getStringExtra("map_radius"));
        }
        else{
            distance_parametered = 500;
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            enableUserLocation();
            zoomToUserLocation();
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        }

        putAllMarker();
        CircleOptions circleOpt = new CircleOptions();
        circleOpt.radius(100000);
        circleOpt.strokeColor(Color.GRAY);
        circleOpt.fillColor(Color.GRAY);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                circleOpt.center(latLng);
                mMap.addCircle(circleOpt);
                Toast.makeText(MapsActivity.this, Double.toString(distance_parametered), Toast.LENGTH_SHORT).show();
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                Intent intent = new Intent(MapsActivity.this, AddSitesActivity.class);
                intent.putExtra("click_latitude", String.valueOf(latLng.latitude));
                intent.putExtra("click_longitude", String.valueOf(latLng.longitude));
                startActivityForResult(intent, 3);
            }
        });

    }

    @SuppressLint("MissingPermission")
    public void enableUserLocation(){
        mMap.setMyLocationEnabled(true);
    }


    private void zoomToUserLocation(){
        locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == ACCESS_LOCATION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                enableUserLocation();
                zoomToUserLocation();
            }
            else{

            }
        }
    }

    public void putMarkerInDistance(double distance){
        ArrayList<Site> listSites = daoSite.getAllSites(this);

        for (int i = 0; i < listSites.size(); i++) {
            if(distance_parametered>=distance) {
                LatLng site_positions = new LatLng(listSites.get(i).getLatitude(), listSites.get(i).getLongitude());
                mMap.addMarker(new MarkerOptions().position(site_positions).title(listSites.get(i).getName() + " - " + listSites.get(i).getResume() + " - " + distance));
            }
        }
    }

    public void putAllMarker(){
        ArrayList<Site> listSites = daoSite.getAllSites(this);

        for (int i = 0; i < listSites.size(); i++) {
            LatLng site_positions = new LatLng(listSites.get(i).getLatitude(),listSites.get(i).getLongitude());

            mMap.addMarker(new MarkerOptions().position(site_positions).title(listSites.get(i).getName()+" - "+listSites.get(i).getResume()));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.button_menu_parameters:
                Intent intent_parameters = new Intent(MapsActivity.this, MapsParametersActivity.class);
                startActivity(intent_parameters);
                break;
            case R.id.button_menu_sites:
                Intent intent_sites = new Intent(MapsActivity.this, SitesActivity.class);
                intent_sites.putExtra("current_position", String.valueOf(locationTask));
                startActivity(intent_sites);
                break;
            case R.id.button_menu_categories:
                Intent intent_categorie = new Intent(MapsActivity.this, CategoriesActivity.class);
                startActivity(intent_categorie);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}