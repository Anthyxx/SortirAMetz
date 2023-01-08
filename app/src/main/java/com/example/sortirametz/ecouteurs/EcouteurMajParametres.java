package com.example.sortirametz.ecouteurs;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.sortirametz.activities.MapsActivity;
import com.example.sortirametz.activities.MapsParametersActivity;

public class EcouteurMajParametres implements View.OnClickListener{
    MapsParametersActivity activity;

    public EcouteurMajParametres(MapsParametersActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        if(activity.var_edit_maps_parameters_radius.getText().toString().trim().matches("[0-9]+")){
            Intent intent = new Intent(activity , MapsActivity.class);
            intent.putExtra("map_category", activity.var_spinner_categories_parameters.getSelectedItem().toString().trim());
            intent.putExtra("map_radius", activity.var_edit_maps_parameters_radius.getText().toString().trim());
            activity.setResult(3, intent);
            activity.finish();
        }
        else{
            Toast.makeText(activity, "Radius: numbers only, at least 1", Toast.LENGTH_SHORT).show();
        }
    }
}