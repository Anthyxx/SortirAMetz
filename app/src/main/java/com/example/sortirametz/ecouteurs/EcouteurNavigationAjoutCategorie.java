package com.example.sortirametz.ecouteurs;

import android.content.Intent;
import android.view.View;

import com.example.sortirametz.activities.AddCategoriesActivity;
import com.example.sortirametz.activities.CategoriesActivity;

public class EcouteurNavigationAjoutCategorie implements View.OnClickListener{
    CategoriesActivity activity;

    public EcouteurNavigationAjoutCategorie(CategoriesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, AddCategoriesActivity.class);
        activity.startActivity(intent);
    }
}
