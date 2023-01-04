package com.example.sortirametz.dao;

import static java.lang.Float.parseFloat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.sortirametz.bdd.ContractClass;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

import java.util.ArrayList;

public class DAOSite {

    public ArrayList<Site> getAllSites(Activity activity){
        ContentResolver contentResolver = activity.getContentResolver();
        ArrayList<Site> listSites = new ArrayList<>();
        Cursor c = contentResolver.query(ContractClass.Site.CONTENT_URI, null, null, null, null);
        if (c.moveToFirst() ){
            do {
                // Passing values
                int column1_id = c.getInt(0);
                String column2_name = c.getString(1);
                float column3_latitude = parseFloat(c.getString(2));
                float column4_longitude = parseFloat(c.getString(3));
                String column5_adresse = c.getString(4);
                String column6_categorie = c.getString(5);
                String column7_resume = c.getString(6);

                listSites.add(new Site(column1_id,column2_name,column3_latitude,column4_longitude,column5_adresse,column6_categorie,column7_resume));
            } while(c.moveToNext());
        }
        c.close();
        return listSites;
    }

    public void addSite(Activity activity, Site site){
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues cv = new ContentValues();

        cv.put(ContractClass.Site.site_name, site.getName());
        cv.put(ContractClass.Site.latitude, site.getLatitude());
        cv.put(ContractClass.Site.longitude, site.getLongitude());
        cv.put(ContractClass.Site.adresse_postale, site.getAdresse());
        cv.put(ContractClass.Site.site_category_name, site.getCategorie());
        cv.put(ContractClass.Site.resume, site.getResume());

        contentResolver.insert(ContractClass.Site.CONTENT_URI,cv);
    }

    public void updateSite(Activity activity, Site site){
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues cv = new ContentValues();

        cv.put(ContractClass.Site.site_name, site.getName());
        cv.put(ContractClass.Site.latitude, site.getLatitude());
        cv.put(ContractClass.Site.longitude, site.getLongitude());
        cv.put(ContractClass.Site.adresse_postale, site.getAdresse());
        cv.put(ContractClass.Site.site_category_name, site.getCategorie());
        cv.put(ContractClass.Site.resume, site.getResume());

        contentResolver.update(ContractClass.Site.CONTENT_URI, cv ,"id=?", new String[]{Integer.toString(site.getId())});
    }

    public void deleteSite(Activity activity, String id){//SitesListActivity, int id
        ContentResolver contentResolver = activity.getContentResolver();
        String[] selectionArgs = new String[]{String.valueOf(id)};
        contentResolver.delete(ContractClass.Site.CONTENT_URI, "id = ?", selectionArgs);
    }
}
