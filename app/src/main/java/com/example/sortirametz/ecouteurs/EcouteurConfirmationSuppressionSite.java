package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.UpdateSitesActivity;

public class EcouteurConfirmationSuppressionSite implements View.OnClickListener{
    UpdateSitesActivity activity;

    public EcouteurConfirmationSuppressionSite(UpdateSitesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.confirmDialog();
    }
}
