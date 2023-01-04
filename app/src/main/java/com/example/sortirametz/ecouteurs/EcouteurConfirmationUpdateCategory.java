package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.UpdateCategoriesActivity;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.modeles.Categorie;

public class EcouteurConfirmationUpdateCategory implements View.OnClickListener{
    UpdateCategoriesActivity activity;

    public EcouteurConfirmationUpdateCategory(UpdateCategoriesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.categorie.setName(activity.var_edit_update_name_category.getText().toString().trim());
        activity.daoCategorie.updateCategory(activity, activity.categorie);
        activity.finish();
    }
}
