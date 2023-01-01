package com.example.sortirametz.customadaptater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sortirametz.R;
import com.example.sortirametz.activities.UpdateCategoriesActivity;
import com.example.sortirametz.dao.DAOCategorie;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

import java.util.ArrayList;

public class CustomAdaptaterCategories extends RecyclerView.Adapter<CustomAdaptaterCategories.MyViewHolderCategories> {
    Context context;
    Activity activity;
    ArrayList<Categorie> listCategories;

    DAOCategorie daoCategorie = new DAOCategorie();

    public CustomAdaptaterCategories(Context context, Activity activity, ArrayList<Categorie> listCategories) {
        this.context = context;
        this.activity = activity;
        this.listCategories = listCategories;
    }

    @NonNull
    @Override
    public CustomAdaptaterCategories.MyViewHolderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_view_categories, parent, false);
        return new MyViewHolderCategories(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCategories holder, int position) {
        holder.var_categorie_id_txt.setText(String.valueOf(listCategories.get(position).getId()));
        holder.var_categorie_name_txt.setText(String.valueOf(listCategories.get(position).getName()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateCategoriesActivity.class);
                intent.putExtra("category_id", String.valueOf(listCategories.get(position).getId()));
                intent.putExtra("category_name", String.valueOf(listCategories.get(position).getName()));
                activity.startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public class MyViewHolderCategories extends RecyclerView.ViewHolder{
        TextView var_categorie_id_txt, var_categorie_name_txt;
        LinearLayout layout;

        public MyViewHolderCategories(@NonNull View itemView) {
            super(itemView);
            var_categorie_id_txt = itemView.findViewById(R.id.categorie_id_txt);
            var_categorie_name_txt = itemView.findViewById(R.id.categorie_name_txt);
            layout = itemView.findViewById(R.id.categorie_layout);
        }
    }
}
