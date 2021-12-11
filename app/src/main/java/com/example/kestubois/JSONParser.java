package com.example.kestubois;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kestubois.classSimple.Cocktail;
import com.example.kestubois.classSimple.Ingredient;
import com.example.kestubois.classSimple.TempObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class JSONParser {

    private String url = "http://192.168.1.33/Halcoletmie/index.php";
    private InterfaceController mCallBack ;

    public JSONParser(Context context, InterfaceController callBack) {
        this.mCallBack = callBack ;
        Singleton singleton = Singleton.getInstance();
        //Début de la requête http
        RequestQueue queue = Volley.newRequestQueue(context);
        if(!singleton.isLoaded()) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //On parse la réponse http en Json
                        JSONArray jsonArray = new JSONArray(response);

                        ArrayList<TempObject> tempObjects = createTempObject(jsonArray);
                        ArrayList<Integer> idCocktails = new ArrayList<>();
                        //On remplis un tableau d'id de cocktail pour éviter la redondance dans la construction des objets cocktail
                        for (int i = 0; i < tempObjects.size(); i++) {
                            //Si idCocktails ne contiens pas l'id de cocktail en train d'être passé en revu
                            if (!idCocktails.contains(tempObjects.get(i).getIdCocktail())) {
                                idCocktails.add(tempObjects.get(i).getIdCocktail());
                            }
                            //Dans le cas contraire on fait rien
                        }
                        //Il faudra ensuite remplir le singleton
                        ArrayList<Cocktail> cocktails = new ArrayList<>();
                        for (int j = 0; j < idCocktails.size(); j++) {
                            //Le nombre d'identifiant définis le nombre de cocktails a faire
                            Cocktail cocktail = new Cocktail();

                            //Sachant que tout les ingredients sont ici, il faut les faire correspondre a l'idcocktail
                            for (int k = 0; k < tempObjects.size(); k++) {

                                //Si l'id du cocktail en cours est égal a l'id du cocktail temp
                                if (idCocktails.get(j) == tempObjects.get(k).getIdCocktail()) {
                                    //On crée notre nouvel ingrédient
                                    Ingredient ingredient = new Ingredient(
                                            tempObjects.get(k).getNomIngredient(),
                                            tempObjects.get(k).getQuantite()
                                    );
                                    //On ajoute les ingrédients a l'objet cocktail et on construit notre objet
                                    //C'est pas grave si desc et nom se changent plusieurs fois puisqu'ils sont pareils
                                    cocktail.addIngredient(ingredient);
                                    cocktail.setNom(tempObjects.get(k).getNom());
                                    cocktail.setDescription(tempObjects.get(k).getDesc());
                                }
                            }

                            Singleton singleton = Singleton.getInstance();
                            //ajout au singleton du cocktail
                            if (!singleton.isLoaded()) {
                                singleton.addCocktail(cocktail);
                            }
                        }
                        mCallBack.onLoad();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(stringRequest);
        }
    }

    private ArrayList<TempObject> createTempObject(JSONArray jsonArray){
        int jsonArrayLength = jsonArray.length();
        ArrayList<TempObject> cocktailTempList = new ArrayList<>();
        //C'est ici qu'on va build l'objet temporaire permettant ensuite de créer l'objet final
        try {
            for(int i = 0; i < jsonArrayLength; i++){
                TempObject cocktailTemp = new TempObject();
                cocktailTemp.setNom(jsonArray.getJSONObject(i).getString("Nom"));
                cocktailTemp.setDesc(jsonArray.getJSONObject(i).getString("Description"));
                cocktailTemp.setIdCocktail(jsonArray.getJSONObject(i).getInt("IDCocktail"));
                cocktailTemp.setNomIngredient(jsonArray.getJSONObject(i).getString("NomIngredient"));
                cocktailTemp.setQuantite(jsonArray.getJSONObject(i).getString("Quantite"));
                cocktailTempList.add(cocktailTemp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cocktailTempList;
    }

}
