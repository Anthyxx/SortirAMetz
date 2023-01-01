package com.example.sortirametz.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MonProvider extends android.content.ContentProvider{
    DatabaseHelper helper;
    private Context context;

    @Override
    public boolean onCreate() {
        this.helper = new DatabaseHelper(this.getContext());
        return true;    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd;
        bd = this.helper.getReadableDatabase();
        String nomTable = uri.getPath().substring(1);
        return bd.query(nomTable, projection, selection, selectionArgs, null, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase bd;
        bd = this.helper.getWritableDatabase();
        String nomTable = uri.getPath().substring(1);
        long result = bd.insert(nomTable, null, contentValues);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd;
        bd = this.helper.getWritableDatabase();
        String nomTable = uri.getPath().substring(1);
        return bd.delete(nomTable, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
