package com.example.sortirametz.ecouteurs;

import android.view.View;
import android.widget.Toast;

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
        String oldNameCategory = activity.categorie.getName();
        if(activity.var_edit_update_name_category.getText().toString().trim().matches("[a-zA-Z0-9 ]*")){
            activity.categorie.setName(activity.var_edit_update_name_category.getText().toString().trim());
            activity.daoCategorie.updateCategory(activity, activity.categorie, oldNameCategory);
            Toast.makeText(activity, "Edited Successfully", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
        else{
            Toast.makeText(activity, "Name: letters, spaces & numbers only", Toast.LENGTH_SHORT).show();
        }
    }
}
