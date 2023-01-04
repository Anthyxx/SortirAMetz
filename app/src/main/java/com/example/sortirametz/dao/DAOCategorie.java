package com.example.sortirametz.dao;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.sortirametz.bdd.ContractClass;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

import java.util.ArrayList;
import java.util.Objects;

public class DAOCategorie {

    public ArrayList<Categorie> getAllCategories(Activity activity){
        ContentResolver contentResolver = activity.getContentResolver();
        ArrayList<Categorie> listCategories = new ArrayList<>();
        Cursor c = contentResolver.query(ContractClass.Categorie.CONTENT_URI, null, null, null, null);
        if (c.moveToFirst() ){
            do {
                // Passing values
                int column1_id = c.getInt(0);
                String column2_name = c.getString(1);
                listCategories.add(new Categorie(column1_id,column2_name));
            } while(c.moveToNext());
        }
        c.close();
        return listCategories;
    }

    public int getCategoryByString(ArrayList<Categorie> list, String category_name){
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            System.out.println("getname = "+list.get(i).getName());
            System.out.println("chaine = "+category_name);
            if(Objects.equals(list.get(i).getName(), category_name)){
                position = i+1;
            }
        }
        return position;
    }

    public void addCategory(Activity activity, Categorie categorie){
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues cv = new ContentValues();

        cv.put(ContractClass.Categorie.category_name, categorie.getName());

        contentResolver.insert(ContractClass.Categorie.CONTENT_URI,cv);
    }

    public void updateCategory(Activity activity, Categorie categorie){
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues cv = new ContentValues();

        cv.put(ContractClass.Categorie.category_name, categorie.getName());

        contentResolver.update(ContractClass.Categorie.CONTENT_URI, cv ,"id=?", new String[]{Integer.toString(categorie.getId())});
    }

    public void deleteCategory(Activity activity, String id){//SitesListActivity, int id
        ContentResolver contentResolver = activity.getContentResolver();
        String[] selectionArgs = new String[]{String.valueOf(id)};
        contentResolver.delete(ContractClass.Categorie.CONTENT_URI, "id = ?", selectionArgs);
    }
}
