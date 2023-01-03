package com.example.sortirametz.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.ecouteurs.EcouteurNavigationSitePositionActuelle;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.sortirametz.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int ACCESS_LOCATION_REQUEST_CODE = 10001;

    String category = "All";
    double distance_radius = 50;
    FloatingActionButton var_button_add_site_currentlocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Task<Location> locationTask;

    CircleOptions circleOpt = new CircleOptions();

    public DAOSite daoSite = new DAOSite();
    //ArrayList<Site> listSites = daoSite.getAllSites(this);

    EcouteurNavigationSitePositionActuelle ecouteurNavigationSitePositionActuelle;

    public double latitudeActuelle;
    public double longitudeActuelle;

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        var_button_add_site_currentlocation = findViewById(R.id.button_add_site_currentlocation);

        ecouteurNavigationSitePositionActuelle = new EcouteurNavigationSitePositionActuelle(this);
        var_button_add_site_currentlocation.setOnClickListener(ecouteurNavigationSitePositionActuelle);


        //fusedLocationProviderClient_old = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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

        putMarkerInDistance();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                putCircle(latLng.latitude, latLng.longitude);
                putMarkerInDistance(latLng);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                Intent intent = new Intent(MapsActivity.this, AddSitesActivity.class);
                intent.putExtra("click_latitude", String.valueOf(latLng.latitude));
                intent.putExtra("click_longitude", String.valueOf(latLng.longitude));
                startActivity(intent);
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
                mMap.clear();
                latitudeActuelle = location.getLatitude();
                longitudeActuelle = location.getLongitude();
                putCircle(location.getLatitude(), location.getLongitude());
                putMarkerInDistance();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
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

    public void putCircle(double latitude, double longitude){
        circleOpt.radius(distance_radius);
        circleOpt.strokeColor(Color.GRAY);
        circleOpt.fillColor(0x22000000);
        circleOpt.center(new LatLng(latitude, longitude));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(bitmapDescriptorFromVector(this, R.drawable.ic_baseline_where_to_vote_24)));
        circleOpt.strokeWidth(10);
        mMap.addCircle(circleOpt);
        Toast.makeText(MapsActivity.this, category, Toast.LENGTH_SHORT).show();
    }

    public void putMarkerInDistance(){
        ArrayList<Site> listSites = daoSite.getAllSites(this);
        locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                putCircle(location.getLatitude(), location.getLongitude());
                for (int i = 0; i < listSites.size(); i++) {
                    float[] distance = new float[1];
                    Location.distanceBetween(listSites.get(i).getLatitude(), listSites.get(i).getLongitude(), location.getLatitude(), location.getLongitude(), distance);
                    if(distance_radius>=distance[0] && (listSites.get(i).getCategorie() == category || category == "All")) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(listSites.get(i).getLatitude(), listSites.get(i).getLongitude())).title(listSites.get(i).getName() + " - " + listSites.get(i).getResume() + " - " + (int)distance[0] + " meters"));
                    }
                }
            }
        });

    }

    public void putMarkerInDistance(LatLng latLng){
        ArrayList<Site> listSites = daoSite.getAllSites(this);
        for (int i = 0; i < listSites.size(); i++) {
            float[] distance = new float[1];
            Location.distanceBetween(listSites.get(i).getLatitude(), listSites.get(i).getLongitude(), latLng.latitude, latLng.longitude, distance);
            if(distance_radius>=distance[0] && (listSites.get(i).getCategorie() == category || category == "All")) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(listSites.get(i).getLatitude(), listSites.get(i).getLongitude())).title(listSites.get(i).getName() + " - " + listSites.get(i).getResume() + " - " + (int)distance[0] + " meters"));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3){
            category = data.getStringExtra("map_category");
            distance_radius = Double.parseDouble(data.getStringExtra("map_radius"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.button_menu_parameters:
                Intent intent_parameters = new Intent(MapsActivity.this, MapsParametersActivity.class);
                intent_parameters.putExtra("radius", Double.toString(distance_radius));
                startActivityForResult(intent_parameters, 3);
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
