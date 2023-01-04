package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.MapsActivity;

public class EcouteurRefreshMap implements View.OnClickListener{
    MapsActivity activity;

    public EcouteurRefreshMap(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.putMarkerInDistance();
        activity.zoomToUserLocation();
    }
}
