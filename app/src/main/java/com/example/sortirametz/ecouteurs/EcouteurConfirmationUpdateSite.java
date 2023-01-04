package com.example.sortirametz.ecouteurs;

import android.view.View;

import com.example.sortirametz.activities.UpdateSitesActivity;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.modeles.Site;

public class EcouteurConfirmationUpdateSite implements View.OnClickListener{
    UpdateSitesActivity activity;

    public EcouteurConfirmationUpdateSite(UpdateSitesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.site.setName(activity.var_edit_update_name_site.getText().toString().trim());
        activity.site.setLatitude(Double.parseDouble(activity.var_edit_update_latitude_site.getText().toString().trim()));
        activity.site.setLongitude(Double.parseDouble(activity.var_edit_update_longitude_site.getText().toString().trim()));
        activity.site.setAdresse(activity.var_edit_update_address_site.getText().toString().trim());
        activity.site.setCategorie(activity.var_spinner_update_category_site.getSelectedItem().toString().trim());
        activity.site.setResume(activity.var_edit_update_resume_site.getText().toString().trim());
        activity.daoSite.updateSite(activity, activity.site);
        activity.finish();

    }
}
