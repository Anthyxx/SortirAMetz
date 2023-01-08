package com.example.sortirametz.ecouteurs;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sortirametz.activities.MapsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class EcouteurMapClick implements GoogleMap.OnMapClickListener{
    MapsActivity activity;

    public EcouteurMapClick(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if(activity.modeUserLocation){
            activity.modeUserLocation = false;
            Toast.makeText(activity, activity.modeUserLocationNotEnabled, Toast.LENGTH_SHORT).show();
        }
        if(activity.modeAllSite){
            activity.modeAllSite = false;
            Toast.makeText(activity, activity.modeUserLocationNotEnabled, Toast.LENGTH_SHORT).show();
        }
        activity.mMap.clear();
        activity.modeAllSite = false;
        activity.putCircle(latLng.latitude, latLng.longitude);
        activity.putMarkerInDistance(latLng);
    }
}
