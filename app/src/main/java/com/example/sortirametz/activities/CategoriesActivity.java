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
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.modeles.Categorie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper db;
    CustomAdaptaterCategories adaptater;
    ContentResolver contentResolver;
    FloatingActionButton var_btn_add_category;

    DAOCategorie dao_categorie = new DAOCategorie();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        recyclerView = findViewById(R.id.recycler_categorie);
        var_btn_add_category = findViewById(R.id.button_add_category);

        this.contentResolver = this.getContentResolver();

        var_btn_add_category.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriesActivity.this, AddCategoriesActivity.class);
                startActivity(intent);
            }
        });

        db = new DatabaseHelper(CategoriesActivity.this);

        displayCategories();

        adaptater = new CustomAdaptaterCategories(CategoriesActivity.this, this, dao_categorie.getAllCategories(this));
        recyclerView.setAdapter(adaptater);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoriesActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            recreate();
        }
    }

    void displayCategories() {
        ArrayList<Categorie> listCategories = dao_categorie.getAllCategories(this);
        for (int i = 0; i < listCategories.size(); i++) {
            listCategories.get(i).getId();
            listCategories.get(i).getName();
        }
    }
}
