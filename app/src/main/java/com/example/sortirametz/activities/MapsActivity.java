package com.example.sortirametz.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.ecouteurs.EcouteurAfficheAllSites;
import com.example.sortirametz.ecouteurs.EcouteurMapClick;
import com.example.sortirametz.ecouteurs.EcouteurMapLongClick;
import com.example.sortirametz.ecouteurs.EcouteurNavigationSitePositionActuelle;
import com.example.sortirametz.ecouteurs.EcouteurRefreshMap;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.sortirametz.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int ACCESS_LOCATION_REQUEST_CODE = 10001;

    String category = "All";
    double distance_radius = 50;
    FloatingActionButton var_button_show_all_sites, var_button_add_site_currentlocation, var_button_refresh;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Task<Location> locationTask;
    LocationRequest locationRequest;

    public boolean modeUserLocation;
    public boolean modeAllSite;

    public String modeAllSiteEnabled = "Mode \"All Sites\" enabled";
    public String modeUserLocationEnabled = "Mode \"Sites around you\" enabled";
    public String modeUserLocationNotEnabled = "Mode \"Sites around clic\" enabled";

    CircleOptions circleOpt = new CircleOptions();

    public DAOSite daoSite = new DAOSite();
    //ArrayList<Site> listSites = daoSite.getAllSites(this);

    EcouteurAfficheAllSites ecouteurAfficheAllSites;
    EcouteurNavigationSitePositionActuelle ecouteurNavigationSitePositionActuelle;
    EcouteurRefreshMap ecouteurRefreshMap;
    EcouteurMapClick ecouteurMapClick;
    EcouteurMapLongClick ecouteurMapLongClick;

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
        var_button_show_all_sites = findViewById(R.id.button_show_all_sites);
        var_button_add_site_currentlocation = findViewById(R.id.button_add_site_currentlocation);
        var_button_refresh = findViewById(R.id.button_refresh);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        modeUserLocation = true;
        modeAllSite = false;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        startLocationUpdate();
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
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            Context context = getApplicationContext();

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(MapsActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(MapsActivity.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(MapsActivity.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        //buttons listeners
        ecouteurAfficheAllSites = new EcouteurAfficheAllSites(this);
        var_button_show_all_sites.setOnClickListener(ecouteurAfficheAllSites);

        ecouteurNavigationSitePositionActuelle = new EcouteurNavigationSitePositionActuelle(this);
        var_button_add_site_currentlocation.setOnClickListener(ecouteurNavigationSitePositionActuelle);

        ecouteurRefreshMap = new EcouteurRefreshMap(this);
        var_button_refresh.setOnClickListener(ecouteurRefreshMap);

        ecouteurMapClick = new EcouteurMapClick(this);
        mMap.setOnMapClickListener(ecouteurMapClick);

        ecouteurMapLongClick = new EcouteurMapLongClick(this);
        mMap.setOnMapLongClickListener(ecouteurMapLongClick);


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

        if(modeUserLocation){
            startLocationUpdate();
        }
        else{
            stopLocationUpdate();
            putMarkerInDistance();
        }
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(modeUserLocation){
                mMap.clear();
                putMarkerInDistance();
                putCircle(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
            }
        }
    };

    private void startLocationUpdate() {
        if (modeUserLocation){
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    private void stopLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    public void enableUserLocation(){
        mMap.setMyLocationEnabled(true);
    }


    public void zoomToUserLocation(){
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
        //Toast.makeText(MapsActivity.this, category, Toast.LENGTH_SHORT).show();
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
                    if(distance_radius>=distance[0] && (Objects.equals(listSites.get(i).getCategorie(), category) || Objects.equals(category, "All"))) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(listSites.get(i).getLatitude(), listSites.get(i).getLongitude())).title(listSites.get(i).getName() + " (at " + (int)distance[0] + " meters)").snippet(listSites.get(i).getResume()));
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
            if(distance_radius>=distance[0] && (Objects.equals(listSites.get(i).getCategorie(), category) || Objects.equals(category, "All"))) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(listSites.get(i).getLatitude(), listSites.get(i).getLongitude())).title(listSites.get(i).getName() + " (at " + (int)distance[0] + " meters)").snippet(listSites.get(i).getResume()));
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
                intent_parameters.putExtra("category", category);
                intent_parameters.putExtra("radius", Integer.toString((int)distance_radius));
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
