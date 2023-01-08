package com.example.sortirametz.ecouteurs;

import android.view.View;
import android.widget.Toast;

import com.example.sortirametz.activities.MapsActivity;

public class EcouteurRefreshMap implements View.OnClickListener{
    MapsActivity activity;

    public EcouteurRefreshMap(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        if(!activity.modeUserLocation){
            activity.modeUserLocation = true;
            Toast.makeText(activity, activity.modeUserLocationEnabled, Toast.LENGTH_SHORT).show();
        }
        if(activity.modeAllSite){
            activity.modeAllSite = false;
        }
        activity.putMarkerInDistance();
        activity.zoomToUserLocation();

    }
}
