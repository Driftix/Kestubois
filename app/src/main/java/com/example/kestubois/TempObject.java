package com.example.kestubois;

public class TempObject {
    private String nom;
    private String desc;
    private int idCocktail;
    private String nomIngredient;
    private float quantite;

    public TempObject(String nom, String desc, int idCocktail, String nomIngredient, String quantite){
        this.nom = nom;
        this.desc = desc;
        this.idCocktail = idCocktail;
        this.nomIngredient = nomIngredient;
        this.quantite = Float.parseFloat(quantite);
    }
    public TempObject(){}

    public void setQuantite(String quantite) {
        this.quantite = Float.parseFloat(quantite);
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setIdCocktail(int idCocktail) {
        this.idCocktail = idCocktail;
    }
    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public int getIdCocktail() {
        return idCocktail;
    }
    public float getQuantite() {
        return quantite;
    }
    public String getNom() {
        return nom;
    }
    public String getDesc() {
        return desc;
    }
    public String getNomIngredient() {
        return nomIngredient;
    }
}
