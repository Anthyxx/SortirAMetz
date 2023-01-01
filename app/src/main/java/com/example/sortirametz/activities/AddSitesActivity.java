package com.example.sortirametz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

public class AddSitesActivity extends AppCompatActivity {
    EditText var_edit_add_name_site, var_edit_add_latitude_site, var_edit_add_longitude_site, var_edit_add_address_site, var_edit_add_category_site, var_edit_add_resume_site;
    Button var_btn_confirmation_add_site;

    DAOSite daoSite = new DAOSite();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        var_edit_add_name_site = findViewById(R.id.edit_add_name_site);
        var_edit_add_latitude_site = findViewById(R.id.edit_add_latitude_site);
        var_edit_add_longitude_site = findViewById(R.id.edit_add_longitude_site);
        var_edit_add_address_site = findViewById(R.id.edit_add_address_site);
        var_edit_add_category_site = findViewById(R.id.edit_add_category_site);
        var_edit_add_resume_site = findViewById(R.id.edit_add_resume_site);

        var_btn_confirmation_add_site = findViewById(R.id.btn_confirmation_add_site);

        var_btn_confirmation_add_site.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                float var_float_latitude = Float.valueOf(var_edit_add_latitude_site.getText().toString().trim());
                float var_float_longitude = Float.valueOf(var_edit_add_longitude_site.getText().toString().trim());
                daoSite.addSite(AddSitesActivity.this,
                        new Site(
                                var_edit_add_name_site.getText().toString().trim(),
                                var_float_latitude,
                                var_float_longitude,
                                var_edit_add_address_site.getText().toString().trim(),
                                var_edit_add_category_site.getText().toString().trim(),
                                var_edit_add_resume_site.getText().toString().trim()
                                )
                );
            }
        });
    }
}
