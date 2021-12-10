package com.example.kestubois.classSimple;

import java.util.ArrayList;

public class Cocktail {
    private String nom;
    private String description;
    private ArrayList<Ingredient> ingredients;

    public Cocktail(){
        this.ingredients = new ArrayList<Ingredient>();
    }
    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public String getDescription() {
        return description;
    }
    public String getNom() {
        return nom;
    }
}
