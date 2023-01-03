package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.AddCategoriesActivity;
import com.example.sortirametz.activities.AddSitesActivity;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.modeles.Categorie;

public class EcouteurConfirmationAjoutCategorie implements View.OnClickListener {
    AddCategoriesActivity activity;
    DAOCategorie daoCategorie = new DAOCategorie();


    public EcouteurConfirmationAjoutCategorie(AddCategoriesActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onClick(View view) {
        daoCategorie.addCategory(activity,
                new Categorie(
                        activity.var_edit_add_category_category.getText().toString().trim()
                )
        );
    }
}