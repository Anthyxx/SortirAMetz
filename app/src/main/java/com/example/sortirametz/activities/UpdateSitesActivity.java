package com.example.sortirametz.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.ecouteurs.EcouteurConfirmationSuppressionSite;
import com.example.sortirametz.ecouteurs.EcouteurConfirmationUpdateSite;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

import java.util.ArrayList;

public class UpdateSitesActivity extends AppCompatActivity {
    public EditText var_edit_update_name_site;
    public EditText var_edit_update_latitude_site;
    public EditText var_edit_update_longitude_site;
    public EditText var_edit_update_address_site;
    public EditText var_edit_update_resume_site;
    Button var_btn_confirmation_update_site, var_btn_confirmation_delete_site;
    public Spinner var_spinner_update_category_site;

    String site_id, site_name, site_latitude, site_longitude, site_address, site_category, site_resume;

    public Site site;

    EcouteurConfirmationSuppressionSite ecouteurConfirmationSuppressionSite;
    EcouteurConfirmationUpdateSite ecouteurConfirmationUpdateSite;

    public DAOSite daoSite = new DAOSite();
    DAOCategorie daoCategorie = new DAOCategorie();

    ArrayList<Categorie> listCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_site);

        var_edit_update_name_site = findViewById(R.id.edit_update_name_site);
        var_edit_update_latitude_site = findViewById(R.id.edit_update_latitude_site);
        var_edit_update_longitude_site = findViewById(R.id.edit_update_longitude_site);
        var_edit_update_address_site = findViewById(R.id.edit_update_address_site);
        var_spinner_update_category_site = findViewById(R.id.spinner_update_category_site);
        var_edit_update_resume_site = findViewById(R.id.edit_update_resume_site);

        var_btn_confirmation_update_site = findViewById(R.id.btn_confirmation_update_site);
        var_btn_confirmation_delete_site = findViewById(R.id.btn_confirmation_delete_site);

        listCategories = daoCategorie.getAllCategories(this);
        ArrayList<String> listOptions = new ArrayList<String>();
        listOptions.add("None");
        for (int i = 0; i < listCategories.size(); i++) {
            listOptions.add(listCategories.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_list, listOptions);
        var_spinner_update_category_site.setAdapter(adapter); // this will set list of values to spinner

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(site_name);
        }

        ecouteurConfirmationSuppressionSite = new EcouteurConfirmationSuppressionSite(this);
        ecouteurConfirmationUpdateSite = new EcouteurConfirmationUpdateSite(this);

        var_btn_confirmation_delete_site.setOnClickListener(ecouteurConfirmationSuppressionSite);
        var_btn_confirmation_update_site.setOnClickListener(ecouteurConfirmationUpdateSite);
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
            //System.out.println("indice = "+daoCategorie.getCategoryByString(listCategories, site_category));
            var_spinner_update_category_site.setSelection(daoCategorie.getCategoryByString(listCategories, site_category));
            var_edit_update_resume_site.setText(site_resume);

            site = new Site(Integer.parseInt(site_id), site_name, Double.parseDouble(site_latitude), Double.parseDouble(site_longitude), site_address, site_category, site_resume);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmDialog(){
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
