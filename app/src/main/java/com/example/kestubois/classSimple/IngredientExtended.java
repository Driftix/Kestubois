package com.example.kestubois.classSimple;

public class IngredientExtended extends Ingredient{
    private boolean isQuote;
    public IngredientExtended(String nom, float quantite) {
        super(nom, quantite);
        this.isQuote = false;
    }


    public void setIsQuote(boolean isQuote){
        this.isQuote = isQuote;
    }
    public boolean getIsQuote(){
        return this.isQuote;
    }
}
