package com.example.kestubois.classSimple;

public class Ingredient {
    private String nom;
    private float quantite;

    public Ingredient(String nom,float quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }
    public float getQuantite() {
        return quantite;
    }
}
