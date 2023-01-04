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
import com.example.sortirametz.ecouteurs.EcouteurMajParametres;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapsParametersActivity extends AppCompatActivity {
    public EditText var_edit_maps_parameters_radius;
    public Button var_btn_confirmation_update_parameters;
    public Spinner var_spinner_categories_parameters;

    public DAOCategorie daoCategorie = new DAOCategorie();

    EcouteurMajParametres ecouteurMajParametres;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_parameters);

        var_spinner_categories_parameters = findViewById(R.id.spinner_categories_parameters);
        var_edit_maps_parameters_radius = findViewById(R.id.edit_maps_parameters_radius);
        var_btn_confirmation_update_parameters = findViewById(R.id.btn_confirmation_update_parameters);





        ArrayList<Categorie> listCategories = daoCategorie.getAllCategories(this);
        ArrayList<String> listOptions = new ArrayList<String>();
        listOptions.add("All");
        for (int i = 0; i < listCategories.size(); i++) {
            listOptions.add(listCategories.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_list, listOptions);
        var_spinner_categories_parameters.setAdapter(adapter); // this will set list of values to spinner

        String category = getIntent().getStringExtra("category");

        var_spinner_categories_parameters.setSelection(daoCategorie.getCategoryByString(listCategories, category));
        var_edit_maps_parameters_radius.setText(getIntent().getStringExtra("radius"));

        ecouteurMajParametres = new EcouteurMajParametres(this);
        var_btn_confirmation_update_parameters.setOnClickListener(ecouteurMajParametres);
    }
}
