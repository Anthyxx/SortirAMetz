package com.example.sortirametz.customadaptater;

import android.app.Activity;
import android.content.Context;
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
import com.example.sortirametz.activities.UpdateSitesActivity;
import com.example.sortirametz.modeles.Categorie;
import com.example.sortirametz.modeles.Site;

import java.util.ArrayList;

public class CustomAdaptaterSites extends RecyclerView.Adapter<CustomAdaptaterSites.MyViewHolderSites> {
    Context context;
    Activity activity;
    ArrayList<Site> listSites;

    public CustomAdaptaterSites(Context context, Activity activity, ArrayList<Site> listSites) {
        this.context = context;
        this.activity = activity;
        this.listSites = listSites;
    }

    @NonNull
    @Override
    public CustomAdaptaterSites.MyViewHolderSites onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_view_sites, parent, false);
        return new CustomAdaptaterSites.MyViewHolderSites(view);    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdaptaterSites.MyViewHolderSites holder, int position) {
        holder.var_site_id_txt.setText(String.valueOf(listSites.get(position).getId()));
        holder.var_site_name_txt.setText(String.valueOf(listSites.get(position).getName()));
        holder.var_site_latitude_txt.setText(String.valueOf(listSites.get(position).getLatitude()));
        holder.var_site_longitude_txt.setText(String.valueOf(listSites.get(position).getLongitude()));
        holder.var_site_adresse_txt.setText(String.valueOf(listSites.get(position).getAdresse()));
        if(listSites.get(position).getCategorie() == null || listSites.get(position).getCategorie().equals("None")){
            holder.var_site_categorie_txt.setText("No Category");
        }
        else{
            holder.var_site_categorie_txt.setText(String.valueOf(listSites.get(position).getCategorie()));
        }
        holder.var_site_resume_txt.setText(String.valueOf(listSites.get(position).getResume()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateSitesActivity.class);
                intent.putExtra("site_id", String.valueOf(listSites.get(position).getId()));
                intent.putExtra("site_name", String.valueOf(listSites.get(position).getName()));
                intent.putExtra("site_latitude", String.valueOf(listSites.get(position).getLatitude()));
                intent.putExtra("site_longitude", String.valueOf(listSites.get(position).getLongitude()));
                intent.putExtra("site_address", String.valueOf(listSites.get(position).getAdresse()));
                intent.putExtra("site_category", String.valueOf(listSites.get(position).getCategorie()));
                intent.putExtra("site_resume", String.valueOf(listSites.get(position).getResume()));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSites.size();
    }

    public class MyViewHolderSites extends RecyclerView.ViewHolder{
        TextView var_site_id_txt, var_site_name_txt, var_site_latitude_txt, var_site_longitude_txt, var_site_adresse_txt, var_site_categorie_txt, var_site_resume_txt;
        LinearLayout layout;

        public MyViewHolderSites(@NonNull View itemView) {
            super(itemView);
            var_site_id_txt = itemView.findViewById(R.id.site_id_txt);
            var_site_name_txt = itemView.findViewById(R.id.site_name_txt);
            var_site_latitude_txt = itemView.findViewById(R.id.site_latitude_txt);
            var_site_longitude_txt = itemView.findViewById(R.id.site_longitude_txt);
            var_site_adresse_txt = itemView.findViewById(R.id.site_adresse_txt);
            var_site_categorie_txt = itemView.findViewById(R.id.site_category_txt);
            var_site_resume_txt = itemView.findViewById(R.id.site_resume_txt);
            layout = itemView.findViewById(R.id.site_layout);
        }
    }
}
