package com.example.sortirametz.ecouteurs;

import android.view.View;
import android.widget.Toast;

import com.example.sortirametz.activities.MapsActivity;

public class EcouteurAfficheAllSites implements View.OnClickListener{
    MapsActivity activity;

    public EcouteurAfficheAllSites(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        if(!activity.modeAllSite) {
            activity.modeAllSite = true;
            Toast.makeText(activity, activity.modeAllSiteEnabled, Toast.LENGTH_SHORT).show();
        }
        activity.modeUserLocation = false;
        this.activity.mMap.clear();
        this.activity.putAllMarker();
    }
}
