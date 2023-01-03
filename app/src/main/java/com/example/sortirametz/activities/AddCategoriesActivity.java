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
import com.example.sortirametz.ecouteurs.EcouteurConfirmationAjoutCategorie;
import com.example.sortirametz.ecouteurs.EcouteurConfirmationAjoutSite;
import com.example.sortirametz.modeles.Categorie;

public class AddCategoriesActivity extends AppCompatActivity {
    public TextView var_txt_add_category_category;
    public EditText var_edit_add_category_category;
    public Button var_btn_confirmation_add_category;

    EcouteurConfirmationAjoutCategorie ecouteurConfirmationAjoutCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        //var_txt_add_category_category = findViewById(R.id.txt_add_category_category);
        var_edit_add_category_category = findViewById(R.id.edit_add_category_category);
        var_btn_confirmation_add_category = findViewById(R.id.btn_confirmation_add_category);

        ecouteurConfirmationAjoutCategorie = new EcouteurConfirmationAjoutCategorie(this);
        var_btn_confirmation_add_category.setOnClickListener(ecouteurConfirmationAjoutCategorie);
    }
}
