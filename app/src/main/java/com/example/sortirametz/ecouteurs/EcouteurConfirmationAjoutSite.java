package com.example.sortirametz.ecouteurs;

import android.view.View;
import android.widget.Toast;

import com.example.sortirametz.activities.AddSitesActivity;
import com.example.sortirametz.dao.DAOSite;
import com.example.sortirametz.modeles.Site;

public class EcouteurConfirmationAjoutSite implements View.OnClickListener {
    AddSitesActivity activity;
    DAOSite daoSite = new DAOSite();

    public EcouteurConfirmationAjoutSite(AddSitesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        if(activity.var_edit_add_name_site.getText().toString().trim().matches("[a-zA-Z0-9 ]*") &&
        activity.var_edit_add_address_site.getText().toString().trim().matches("[a-zA-Z0-9 ]*") &&
        activity.var_edit_add_resume_site.getText().toString().trim().matches("[a-zA-Z0-9 ]*")) {
            if(activity.var_edit_add_latitude_site.getText().toString().trim().matches("[0-9]+[.]?[0-9]*") &&
            activity.var_edit_add_longitude_site.getText().toString().trim().matches("[0-9]+[.]?[0-9]*")){
                if (activity.var_spinner_add_category_site.getSelectedItem().toString() == "None") {
                    daoSite.addSite(activity,
                            new Site(
                                    activity.var_edit_add_name_site.getText().toString().trim(),
                                    Float.valueOf(activity.var_edit_add_latitude_site.getText().toString().trim()),
                                    Float.valueOf(activity.var_edit_add_longitude_site.getText().toString().trim()),
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
                                    Float.valueOf(activity.var_edit_add_latitude_site.getText().toString().trim()),
                                    Float.valueOf(activity.var_edit_add_longitude_site.getText().toString().trim()),
                                    activity.var_edit_add_address_site.getText().toString().trim(),
                                    activity.var_spinner_add_category_site.getSelectedItem().toString(),
                                    activity.var_edit_add_resume_site.getText().toString().trim()
                            )
                    );
                }
                Toast.makeText(activity, "Added Successfully", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
            else{
                Toast.makeText(activity, "Latitude/Longitude: numbers & single point only", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(activity, "Name/Address/Resume: letters, spaces & numbers only", Toast.LENGTH_SHORT).show();
        }
    }
}
