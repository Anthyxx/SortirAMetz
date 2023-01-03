package com.example.sortirametz.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.ecouteurs.EcouteurConfirmationAjoutSite;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

import java.util.ArrayList;

public class AddSitesActivity extends AppCompatActivity {
    public EditText var_edit_add_name_site;
    public EditText var_edit_add_latitude_site;
    public EditText var_edit_add_longitude_site;
    public EditText var_edit_add_address_site;
    public EditText var_edit_add_resume_site;
    public Spinner var_spinner_add_category_site;
    public Button var_btn_confirmation_add_site;

    EcouteurConfirmationAjoutSite ecouteurConfirmationAjoutSite;

    DAOCategorie daoCategorie = new DAOCategorie();



    String site_latitude, site_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        var_edit_add_name_site = findViewById(R.id.edit_add_name_site);
        var_edit_add_latitude_site = findViewById(R.id.edit_add_latitude_site);
        var_edit_add_longitude_site = findViewById(R.id.edit_add_longitude_site);
        var_edit_add_address_site = findViewById(R.id.edit_add_address_site);
        var_spinner_add_category_site = findViewById(R.id.spinner_update_category_site);
        var_edit_add_resume_site = findViewById(R.id.edit_add_resume_site);

        var_btn_confirmation_add_site = findViewById(R.id.btn_confirmation_add_site);
        ArrayList<Categorie> listCategories = daoCategorie.getAllCategories(this);
        ArrayList<String> listOptions = new ArrayList<String>();
        listOptions.add("None");
        for (int i = 0; i < listCategories.size(); i++) {
            listOptions.add(listCategories.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_list, listOptions);
        var_spinner_add_category_site.setAdapter(adapter); // this will set list of values to spinner

        if(getIntent().hasExtra("click_latitude") && getIntent().hasExtra("click_longitude")){
            site_latitude = getIntent().getStringExtra("click_latitude");
            site_longitude = getIntent().getStringExtra("click_longitude");
            var_edit_add_latitude_site.setText(site_latitude);
            var_edit_add_longitude_site.setText(site_longitude);
        }
        else if(getIntent().hasExtra("latitudeAct") && getIntent().hasExtra("longitudeAct")){
            site_latitude = getIntent().getStringExtra("latitudeAct");
            site_longitude = getIntent().getStringExtra("longitudeAct");
            var_edit_add_latitude_site.setText(site_latitude);
            var_edit_add_longitude_site.setText(site_longitude);
        }

        ecouteurConfirmationAjoutSite = new EcouteurConfirmationAjoutSite(this);
        var_btn_confirmation_add_site.setOnClickListener(ecouteurConfirmationAjoutSite);
    }

    void getIntentData() {

    }
}