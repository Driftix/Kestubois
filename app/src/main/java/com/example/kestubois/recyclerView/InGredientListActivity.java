package com.example.kestubois.recyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.kestubois.Singleton;
import com.example.kestubois.classSimple.Ingredient;
import com.example.kestubois.classSimple.IngredientExtended;
import com.example.kestubois.classVue.PageAccueil;
import com.example.kestubois.classVue.PageCarteMagasin;
import com.example.kestubois.classVue.PageListeCourses;
import com.example.kestubois.databinding.ActivityIngredientLayoutBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InGredientListActivity extends Activity implements OnIngredientClickListener{

    private ActivityIngredientLayoutBinding mBinding;
    private IngredientListAdapter mAdapter ;
    private List<IngredientExtended> ingredientModelList;
    private Singleton singleton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIngredientLayoutBinding.inflate(getLayoutInflater());
        View v = mBinding.getRoot() ;
        singleton = singleton.getInstance();
        mBinding.rvIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //mBinding.rvIngredients.setLayoutManager((new GridLayoutManager(this, 10)));
        mAdapter = new IngredientListAdapter(this,this);
        mBinding.rvIngredients.setAdapter(mAdapter);
        getData();

        mBinding.BTNContinuerSelection4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(InGredientListActivity.this, PageAccueil.class);     // Déclaration d'une fenêtre (Intent)
                startActivity(intent);      // Ouverture de la fenêtre (l'activité)
            }
        });
        mBinding.BTNEffectuerAchat4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(InGredientListActivity.this, PageCarteMagasin.class);     // Déclaration d'une fenêtre (Intent)
                startActivity(intent);      // Ouverture de la fenêtre (l'activité)
            }
        });
        //Doit rester en bas du onCreate()
        setContentView(v);
    }

    private void getData(){
        this.ingredientModelList = singleton.getListeDeCourses().getIngredients();
        mAdapter.FillArray(this.ingredientModelList);
    }

    @Override
    public void onIngredientClick(int position) {
        ingredientModelList.get(position).setIsQuote(!ingredientModelList.get(position).getIsQuote());
        mAdapter.FillArray(ingredientModelList);
    }
}
