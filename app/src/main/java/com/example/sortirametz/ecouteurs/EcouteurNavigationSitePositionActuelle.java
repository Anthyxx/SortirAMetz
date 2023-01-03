package com.example.sortirametz.ecouteurs;

import android.content.Intent;
import android.view.View;

import com.example.sortirametz.activities.AddSitesActivity;
import com.example.sortirametz.activities.MapsActivity;

public class EcouteurNavigationSitePositionActuelle implements View.OnClickListener{
    MapsActivity activity;

    public EcouteurNavigationSitePositionActuelle(MapsActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, AddSitesActivity.class);
        intent.putExtra("latitudeAct", String.valueOf(activity.latitudeActuelle));
        intent.putExtra("longitudeAct", String.valueOf(activity.longitudeActuelle));
        activity.startActivity(intent);
    }
}
