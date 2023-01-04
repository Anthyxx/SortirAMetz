package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.AddSitesActivity;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.modeles.Site;

public class EcouteurConfirmationAjoutSite implements View.OnClickListener {
    AddSitesActivity activity;
    float var_float_latitude;
    float var_float_longitude;
    DAOSite daoSite = new DAOSite();


    public EcouteurConfirmationAjoutSite(AddSitesActivity activity){
        this.activity = activity;
    }


    @Override
    public void onClick(View view) {
        var_float_latitude = Float.valueOf(activity.var_edit_add_latitude_site.getText().toString().trim());
        var_float_longitude = Float.valueOf(activity.var_edit_add_longitude_site.getText().toString().trim());
        if(activity.var_spinner_add_category_site.getSelectedItem().toString() == "None") {
            daoSite.addSite(activity,
                    new Site(
                            activity.var_edit_add_name_site.getText().toString().trim(),
                            var_float_latitude,
                            var_float_longitude,
                            activity.var_edit_add_address_site.getText().toString().trim(),
                            null,
                            activity.var_edit_add_resume_site.getText().toString().trim()
                    )
            );
        }
        else {
            daoSite.addSite(activity,
                    new Site(
                            activity.var_edit_add_name_site.getText().toString().trim(),
                            var_float_latitude,
                            var_float_longitude,
                            activity.var_edit_add_address_site.getText().toString().trim(),
                            activity.var_spinner_add_category_site.getSelectedItem().toString(),
                            activity.var_edit_add_resume_site.getText().toString().trim()
                    )
            );
        }
        activity.finish();
    }
}
