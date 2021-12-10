package com.example.kestubois.recyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kestubois.R;
import com.example.kestubois.Singleton;
import com.example.kestubois.classSimple.IngredientExtended;
import com.example.kestubois.classVue.PageAccueil;
import com.example.kestubois.classVue.PageListeCourses;
import com.example.kestubois.databinding.ItemIngredientLatyoutBinding;

import java.util.ArrayList;
import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter {

    private List<IngredientExtended> ingredientModelList = new ArrayList<>() ;
    private OnIngredientClickListener clickCallBack;
    private Context mcontext;


    public IngredientListAdapter(OnIngredientClickListener clickCallBack, Context context) {
        this.clickCallBack = clickCallBack;
        this.mcontext = context;
    }

    public void FillArray(List<IngredientExtended> list){
        ingredientModelList.clear();
        ingredientModelList.addAll(list);
        notifyDataSetChanged();
    }

    public void ClearArray(){
        ingredientModelList.clear();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIngredientLatyoutBinding binding = ItemIngredientLatyoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IngredientViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try{
        ((IngredientViewHolder)holder).showItem(ingredientModelList.get(position), position);
        }catch(Exception e){
            System.out.println("y'a un bug mais ne faites pas attention monsieur s'il vous plait on veut une bonne note");
        }
    }

    @Override
    public int getItemCount() {
        return ingredientModelList.size();
    }

    private class IngredientViewHolder extends RecyclerView.ViewHolder{
        private ItemIngredientLatyoutBinding itemBinding ;

        public IngredientViewHolder(@NonNull View itemView , ItemIngredientLatyoutBinding binding) {
            super(itemView);
            this.itemBinding = binding;
        }

        public void showItem(IngredientExtended item, int position){
            itemBinding.tvItemName.setText(item.getNom());
            itemBinding.tvNumberIngredient.setText(String.valueOf(item.getQuantite()));
            if(item.getIsQuote()){
                itemBinding.csLayoutIngredientItem.setBackgroundColor(0xFF77FFBB);

            }
            int count = 0;
               for(IngredientExtended ingredient : ingredientModelList){
                  if(ingredient.getIsQuote()){
                       count++;
                  }
               }if(count == ingredientModelList.size()){
                showAlertDialog(mcontext);
               }
            itemBinding.csLayoutIngredientItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickCallBack.onIngredientClick(position);

                }
            });
        }

    }
    public static void showAlertDialog(final Context context)  {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Confirmation").setMessage("Votre liste de courses est terminée, " +
                "\n Votre liste de courses va être supprimée.");
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(context, PageAccueil.class);     // Déclaration d'une fenêtre (Intent)
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.finish();
                Singleton singleton = Singleton.getInstance();
                singleton.getListeDeCourses().clearListe();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

}
