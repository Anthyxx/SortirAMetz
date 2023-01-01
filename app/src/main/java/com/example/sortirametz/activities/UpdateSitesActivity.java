package com.example.sortirametz.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.modeles.Site;

public class UpdateSitesActivity extends AppCompatActivity {
    EditText var_edit_update_name_site, var_edit_update_latitude_site, var_edit_update_longitude_site, var_edit_update_address_site, var_edit_update_category_site, var_edit_update_resume_site;
    Button var_btn_confirmation_update_site, var_btn_confirmation_delete_site;

    String site_id, site_name, site_latitude, site_longitude, site_address, site_category, site_resume;

    DAOSite daoSite = new DAOSite();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_site);

        var_edit_update_name_site = findViewById(R.id.edit_update_name_site);
        var_edit_update_latitude_site = findViewById(R.id.edit_update_latitude_site);
        var_edit_update_longitude_site = findViewById(R.id.edit_update_longitude_site);
        var_edit_update_address_site = findViewById(R.id.edit_update_address_site);
        var_edit_update_category_site = findViewById(R.id.edit_update_category_site);
        var_edit_update_resume_site = findViewById(R.id.edit_update_resume_site);

        var_btn_confirmation_update_site = findViewById(R.id.btn_confirmation_update_site);
        var_btn_confirmation_delete_site = findViewById(R.id.btn_confirmation_delete_site);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(site_name);
        }
/*
        update_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                title=title_input.getText().toString().trim();
                author=author_input.getText().toString().trim();
                pages=pages_input.getText().toString().trim();
                myDB.updateData(id, title, author, pages);
            }
        });*/

        var_btn_confirmation_delete_site.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("site_id") && getIntent().hasExtra("site_name") && getIntent().hasExtra("site_latitude")
        && getIntent().hasExtra("site_longitude") && getIntent().hasExtra("site_address") && getIntent().hasExtra("site_category")
        && getIntent().hasExtra("site_resume")){
            site_id = getIntent().getStringExtra("site_id");
            site_name = getIntent().getStringExtra("site_name");
            site_latitude = getIntent().getStringExtra("site_latitude");
            site_longitude = getIntent().getStringExtra("site_longitude");
            site_address = getIntent().getStringExtra("site_address");
            site_category = getIntent().getStringExtra("site_category");
            site_resume = getIntent().getStringExtra("site_resume");

            var_edit_update_name_site.setText(site_name);
            var_edit_update_latitude_site.setText(site_latitude);
            var_edit_update_longitude_site.setText(site_longitude);
            var_edit_update_address_site.setText(site_address);
            var_edit_update_category_site.setText(site_category);
            var_edit_update_resume_site.setText(site_resume);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ site_name + " ?");
        builder.setMessage("Are you sure you want to delete "+ site_name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                daoSite.deleteSite(UpdateSitesActivity.this, site_id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}