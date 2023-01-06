package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.MapsActivity;

public class EcouteurAfficheAllSites implements View.OnClickListener{
    MapsActivity activity;

    public EcouteurAfficheAllSites(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        this.activity.mMap.clear();
        this.activity.putAllMarker();
    }
}
