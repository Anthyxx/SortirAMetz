package com.example.sortirametz.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sortirametz.R;
import com.example.sortirametz.bdd.DatabaseHelper;
import com.example.sortirametz.customadaptater.CustomAdaptaterCategories;
import com.example.sortirametz.customadaptater.CustomAdaptaterSites;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.ecouteurs.EcouteurNavigationAjoutSite;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SitesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper db;
    CustomAdaptaterSites adaptater;
    ContentResolver contentResolver;
    FloatingActionButton var_btn_add_site;

    EcouteurNavigationAjoutSite ecouteurNavigationAjoutSite;

    DAOSite dao_site = new DAOSite();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_site);
        recyclerView = findViewById(R.id.recycler_site);
        var_btn_add_site = findViewById(R.id.button_add_site);

        this.contentResolver = this.getContentResolver();

        ecouteurNavigationAjoutSite = new EcouteurNavigationAjoutSite(this);
        var_btn_add_site.setOnClickListener(ecouteurNavigationAjoutSite);

        db = new DatabaseHelper(SitesActivity.this);

        displaySites();

        adaptater = new CustomAdaptaterSites(SitesActivity.this, this, dao_site.getAllSites(this));
        recyclerView.setAdapter(adaptater);
        recyclerView.setLayoutManager(new LinearLayoutManager(SitesActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void displaySites(){
        ArrayList<Site> listSites = dao_site.getAllSites(this);
        for (int i = 0; i < listSites.size(); i++) {
            listSites.get(i).getId();
            listSites.get(i).getName();
            listSites.get(i).getLatitude();
            listSites.get(i).getLongitude();
            listSites.get(i).getAdresse();
            listSites.get(i).getCategorie();
            listSites.get(i).getResume();
        }
    }
}
