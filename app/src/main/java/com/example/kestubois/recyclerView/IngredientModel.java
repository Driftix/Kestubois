package com.example.kestubois.recyclerView;

import android.text.BoringLayout;

public class IngredientModel {
    private String nom;
    private float quantite;
    private boolean isSelected;

    public IngredientModel(String nom, float quantite, boolean isSelected) {
        this.nom = nom;
        this.quantite = quantite;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
