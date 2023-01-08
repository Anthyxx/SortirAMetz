package com.example.sortirametz.ecouteurs;

import android.view.View;
import android.widget.Toast;

import com.example.sortirametz.activities.AddCategoriesActivity;
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
        if(activity.var_edit_add_category_category.getText().toString().trim().matches("[a-zA-Z0-9 ]*")){
            daoCategorie.addCategory(activity,
                    new Categorie(
                            activity.var_edit_add_category_category.getText().toString().trim()
                    )
            );
            Toast.makeText(activity, "Added Successfully", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
        else{
            Toast.makeText(activity, "Name: letters, spaces & numbers only", Toast.LENGTH_SHORT).show();
        }
    }
}