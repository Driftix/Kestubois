package com.example.kestubois.classSimple;

import com.example.kestubois.classSimple.Ingredient;

import java.util.ArrayList;

public class ListeDeCourses {
    private ArrayList<IngredientExtended> ingredients;

    public ListeDeCourses(){
        this.ingredients = new ArrayList<>();
    }
    public void addIngredient(String nom, float quantite){
        //On vérifie si l'ingrédient

        boolean exist = false;
        if(ingredients.size() == 0){
            ingredients.add(new IngredientExtended(nom,quantite));
        }else{
            for(Ingredient ingredient : ingredients){
                if(ingredient.getNom().equals(nom)){
                    ingredient.setQuantite(ingredient.getQuantite() + quantite);
                    exist = true;
                }
            }
            if(!exist){
                this.ingredients.add(new IngredientExtended(nom,quantite));
            }
        }
    }
    public void addIngredientTab(ArrayList<IngredientExtended> ingredients){
        this.ingredients = ingredients;
    }
    public void clearListe(){
        this.ingredients = new ArrayList<IngredientExtended>();
    }
    public ArrayList<IngredientExtended> getIngredients(){
        return this.ingredients;
    }



}
