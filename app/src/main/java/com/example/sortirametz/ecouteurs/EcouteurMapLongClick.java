package com.example.sortirametz.ecouteurs;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.sortirametz.activities.AddSitesActivity;
import com.example.sortirametz.activities.MapsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class EcouteurMapLongClick implements GoogleMap.OnMapLongClickListener{
    MapsActivity activity;

    public EcouteurMapLongClick(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        Intent intent = new Intent(activity, AddSitesActivity.class);
        intent.putExtra("click_latitude", String.valueOf(latLng.latitude));
        intent.putExtra("click_longitude", String.valueOf(latLng.longitude));
        activity.startActivity(intent);
    }
}
