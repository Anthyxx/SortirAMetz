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
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

import java.util.ArrayList;

public class AddSitesActivity extends AppCompatActivity {
    EditText var_edit_add_name_site, var_edit_add_latitude_site, var_edit_add_longitude_site, var_edit_add_address_site, var_edit_add_resume_site;
    Spinner var_spinner_add_category_site;
    Button var_btn_confirmation_add_site;

    DAOSite daoSite = new DAOSite();
    DAOCategorie daoCategorie = new DAOCategorie();

    float var_float_latitude;
    float var_float_longitude;

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

        var_btn_confirmation_add_site.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                var_float_latitude = Float.valueOf(var_edit_add_latitude_site.getText().toString().trim());
                var_float_longitude = Float.valueOf(var_edit_add_longitude_site.getText().toString().trim());
                if(var_spinner_add_category_site.getSelectedItem().toString() == "None") {
                    daoSite.addSite(AddSitesActivity.this,
                            new Site(
                                    var_edit_add_name_site.getText().toString().trim(),
                                    var_float_latitude,
                                    var_float_longitude,
                                    var_edit_add_address_site.getText().toString().trim(),
                                    null,
                                    var_edit_add_resume_site.getText().toString().trim()
                            )
                    );
                }
                else {
                    daoSite.addSite(AddSitesActivity.this,
                            new Site(
                                    var_edit_add_name_site.getText().toString().trim(),
                                    var_float_latitude,
                                    var_float_longitude,
                                    var_edit_add_address_site.getText().toString().trim(),
                                    var_spinner_add_category_site.getSelectedItem().toString(),
                                    var_edit_add_resume_site.getText().toString().trim()
                            )
                    );
                }
            }
        });
    }

    void getIntentData() {

    }
}