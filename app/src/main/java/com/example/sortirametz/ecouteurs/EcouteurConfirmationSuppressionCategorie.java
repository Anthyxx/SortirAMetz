package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.UpdateCategoriesActivity;

public class EcouteurConfirmationSuppressionCategorie implements View.OnClickListener{
    UpdateCategoriesActivity activity;

    public EcouteurConfirmationSuppressionCategorie(UpdateCategoriesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.confirmDialog();
    }
}
