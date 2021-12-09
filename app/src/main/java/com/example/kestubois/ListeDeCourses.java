package com.example.kestubois;

import java.util.ArrayList;

public class ListeDeCourses {
    private ArrayList<Ingredient> ingredients;

    public ListeDeCourses(){
        this.ingredients = new ArrayList<>();
    }
    public void addIngredient(String nom, float quantite){
        //On vérifie si l'ingrédient existe, s'il existe on met à jour les nouvelles quantités, sinon on rajoute l'ingrédient
        //Avec le nouvelles quantités
        boolean exist = false;
        if(ingredients.size() == 0){
            ingredients.add(new Ingredient(nom,quantite));
        }else{
            for(Ingredient ingredient : ingredients){
                if(ingredient.getNom().equals(nom)){
                    ingredient.setQuantite(ingredient.getQuantite() + quantite);
                    exist = true;
                }
            }
            if(!exist){
                this.ingredients.add(new Ingredient(nom,quantite));
            }
        }
    }
    public void addIngredientTab(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }
    public ArrayList<Ingredient> getIngredients(){
        return this.ingredients;
    }



}
