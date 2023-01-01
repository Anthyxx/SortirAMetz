package com.example.sortirametz.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sortirametz.R;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.modeles.Categorie;

public class UpdateCategoriesActivity extends AppCompatActivity {
    EditText var_edit_update_name_category;
    Button var_btn_confirmation_update_category, var_btn_confirmation_delete_category;

    String category_id, category_name;

    DAOCategorie daoCategorie = new DAOCategorie();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        var_edit_update_name_category = findViewById(R.id.edit_update_name_category);

        var_btn_confirmation_update_category = findViewById(R.id.btn_confirmation_update_category);
        var_btn_confirmation_delete_category = findViewById(R.id.btn_confirmation_delete_category);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(category_name);
        }
/*
        update_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                title=title_input.getText().toString().trim();
                author=author_input.getText().toString().trim();
                pages=pages_input.getText().toString().trim();
                myDB.updateData(id, title, author, pages);
            }
        });*/

        var_btn_confirmation_delete_category.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("category_id") && getIntent().hasExtra("category_name")){
            category_id = getIntent().getStringExtra("category_id");
            category_name = getIntent().getStringExtra("category_name");
            var_edit_update_name_category.setText(category_name);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ category_name + " ?");
        builder.setMessage("Are you sure you want to delete "+ category_name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                daoCategorie.deleteCategory(UpdateCategoriesActivity.this, category_id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
