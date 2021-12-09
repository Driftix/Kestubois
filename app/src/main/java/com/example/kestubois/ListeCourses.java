package com.example.kestubois;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ListeCourses extends AppCompatActivity {

    ListView LISTE_achats;
    Button BTN_continuerSelection;
    Button BTN_effectuerAchat;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_courses);
        singleton = Singleton.getInstance();



        //************************  Déclaration des éléments dans une liste personnalisée ***********************//
        LISTE_achats = (ListView) findViewById(R.id.LISTE_achats);      // Récupération de l'ID de l'élément
        ArrayList<HashMap<String, String>> listeItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> uneStruc;   // Initialisation d'une structure à mettre dans la liste (appel du fichier ligne.xml)

        //************************  Ajout de données à une structure ***********************//

        //On récupère la liste de course
        ListeDeCourses listeDeCourses = singleton.getListeDeCourses();

        for(Ingredient ingredient : listeDeCourses.getIngredients()){
            uneStruc = new HashMap<>();
            uneStruc.put("Ingredient",ingredient.getNom());
            uneStruc.put("Quantitée",String.valueOf(ingredient.getQuantite()));
            //System.out.println(ingredient.getNom() + " : " + ingredient.getQuantite());
            listeItem.add(uneStruc);
        }
       /* uneStruc = new HashMap<String, String>();
        uneStruc.put("Ingredient","Citron vert");
        uneStruc.put("Quantitée","2");
        listeItem.add(uneStruc);
*/
        //************************  Fin ********************************************//

        //************************  Ajout de données à une structure ***********************//
       /* uneStruc = new HashMap<String, String>();
        uneStruc.put("Ingredient","Sucre");
        uneStruc.put("Quantitée","50g");
        listeItem.add(uneStruc);
        */

        //************************  Fin ********************************************//

        SimpleAdapter adaptateurListe = new SimpleAdapter(this.getBaseContext(), listeItem, R.layout.ligne_achat, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_achat, R.id.LBL_quantitee_achat});       // Création d'un adaptateur d'affichage dans la liste
        LISTE_achats.setAdapter(adaptateurListe);       // Application de l'adaptateur



        BTN_effectuerAchat = (Button) findViewById(R.id.BTN_effectuerAchat);
        BTN_effectuerAchat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ListeCourses.this, CarteMagasin.class);     // Déclaration d'une fenêtre (Intent)
                startActivity(intent);      // Ouverture de la fenêtre (l'activité)
            }
        });



        BTN_continuerSelection = (Button) findViewById(R.id.BTN_continuerSelection);
        BTN_continuerSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ListeCourses.this, MainActivity.class);     // Déclaration d'une fenêtre (Intent)
                startActivity(intent);      // Ouverture de la fenêtre (l'activité)
            }
        });



    }
}