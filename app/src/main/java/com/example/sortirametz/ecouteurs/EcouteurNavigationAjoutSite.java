package com.example.sortirametz.ecouteurs;

import android.content.Intent;
import android.view.View;

import com.example.sortirametz.activities.AddSitesActivity;
import com.example.sortirametz.activities.SitesActivity;

public class EcouteurNavigationAjoutSite implements View.OnClickListener{
    SitesActivity activity;

    public EcouteurNavigationAjoutSite(SitesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, AddSitesActivity.class);
        activity.startActivity(intent);
    }
}
