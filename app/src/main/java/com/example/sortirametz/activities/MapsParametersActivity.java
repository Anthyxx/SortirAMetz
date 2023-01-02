package com.example.sortirametz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapsParametersActivity extends AppCompatActivity {
    EditText var_edit_maps_parameters_radius;
    Button var_btn_confirmation_update_parameters;
    Spinner var_spinner_categories_parameters;

    public DAOCategorie daoCategorie = new DAOCategorie();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_parameters);

        var_spinner_categories_parameters = findViewById(R.id.spinner_categories_parameters);
        var_edit_maps_parameters_radius = findViewById(R.id.edit_maps_parameters_radius);
        var_btn_confirmation_update_parameters = findViewById(R.id.btn_confirmation_update_parameters);

        ArrayList<Categorie> listCategories = daoCategorie.getAllCategories(this);
        ArrayList<String> listOptions = new ArrayList<String>();
        for (int i = 0; i < listCategories.size(); i++) {
            listOptions.add(listCategories.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_list, listOptions);
        var_spinner_categories_parameters.setAdapter(adapter); // this will set list of values to spinner


        var_btn_confirmation_update_parameters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MapsParametersActivity.this, MapsActivity.class);
                intent.putExtra("map_category", var_edit_maps_parameters_radius.getText());
                intent.putExtra("map_radius", var_edit_maps_parameters_radius.getText());
                startActivity(intent);
            }
        });
    }
}
