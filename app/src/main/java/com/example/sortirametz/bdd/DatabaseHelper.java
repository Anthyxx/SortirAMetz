package com.example.sortirametz.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String name = "bdd_app";
    private static final int version = 1;

    public static final String table_name_sites = "sites";
    public static final String table_name_categories = "categories";


    public DatabaseHelper(@Nullable Context context) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE sites (id INTEGER PRIMARY KEY AUTOINCREMENT, site_name TEXT NOT NULL, latitude TEXT NOT NULL, longitude TEXT NOT NULL, " +
                               "adresse_postale TEXT NOT NULL, site_category_name TEXT, resume TEXT NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE categories (id INTEGER PRIMARY KEY AUTOINCREMENT, category_name TEXT NOT NULL)");

        sqLiteDatabase.execSQL("INSERT INTO sites(site_name,latitude,longitude,adresse_postale,site_category_name,resume) VALUES ('Site Test','0', '0','TestAdresse','Musée','ResumeTest')");
        sqLiteDatabase.execSQL("INSERT INTO categories(category_name) VALUES ('Restaurant')");
        sqLiteDatabase.execSQL("INSERT INTO categories(category_name) VALUES ('Musée')");
        sqLiteDatabase.execSQL("INSERT INTO categories(category_name) VALUES ('Monument')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name_sites);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name_categories);
        onCreate(sqLiteDatabase);
    }
}
