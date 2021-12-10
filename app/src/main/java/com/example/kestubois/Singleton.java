package com.example.kestubois;

import com.example.kestubois.classSimple.Cocktail;
import com.example.kestubois.classSimple.IngredientExtended;
import com.example.kestubois.classSimple.ListeDeCourses;

import java.util.ArrayList;

public class Singleton {
    private static Singleton instance; //= new Singleton();
    private ArrayList<Cocktail> cocktailList;
    private boolean loaded;
    private ListeDeCourses listeDeCourses;

    private Singleton(){
        this.listeDeCourses = new ListeDeCourses();
        this.cocktailList = new ArrayList<>();
        this.loaded = false;
    }

    public static void initInstance(){
        if(instance == null){
            instance = new Singleton();
        }
    }

    public static Singleton getInstance(){
        return instance;
    }
    public void clearListeDeCourse(){
        listeDeCourses.clearListe();
    }
    public void addCocktail(Cocktail cocktail){
        this.cocktailList.add(cocktail);
    }
    public ArrayList<Cocktail> getCocktailList(){
        return this.cocktailList;
    }
    public void setLoaded(boolean loaded){
        this.loaded = loaded;
    }
    public boolean isLoaded(){
        return this.loaded;
    }
    public ListeDeCourses getListeDeCourses(){
        return this.listeDeCourses;
    }
    public void setListeDeCourses(ListeDeCourses listeDeCourses){ this.listeDeCourses = listeDeCourses;}

}
