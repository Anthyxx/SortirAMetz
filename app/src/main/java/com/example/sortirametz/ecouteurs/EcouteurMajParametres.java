package com.example.sortirametz.ecouteurs;

import android.content.Intent;
import android.view.View;

import com.example.sortirametz.activities.MapsActivity;
import com.example.sortirametz.activities.MapsParametersActivity;

public class EcouteurMajParametres implements View.OnClickListener{
    MapsParametersActivity activity;

    public EcouteurMajParametres(MapsParametersActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity , MapsActivity.class);
        intent.putExtra("map_category", activity.var_spinner_categories_parameters.getSelectedItem().toString().trim());
        intent.putExtra("map_radius", activity.var_edit_maps_parameters_radius.getText().toString().trim());
        activity.setResult(3, intent);
        activity.finish();
    }
}
