package com.example.kestubois;

import java.util.ArrayList;

public class ListeDeCourses {
    private ArrayList<Ingredient> ingredients;

    public ListeDeCourses(){
        this.ingredients = new ArrayList<>();
    }
    //ça devrais marcher mais je suis toujours arraché donc je sais pas
    public void addIngredient(String nom, float quantite){
        //Faire les trucs
        boolean exist = false;
        if(ingredients.size() == 0){
            ingredients.add(new Ingredient(nom,quantite));
            System.out.println("dans le if");
        }else{
            for(Ingredient ingredient : ingredients){
                if(ingredient.getNom().equals(nom)){
                    System.out.println("dans le equals");
                    ingredient.setQuantite(ingredient.getQuantite() + quantite);
                    exist = true;
                }
            }
            if(!exist){

                System.out.println("dans le else");
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
