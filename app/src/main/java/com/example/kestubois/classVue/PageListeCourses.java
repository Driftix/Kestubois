package com.example.kestubois.classVue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kestubois.classSimple.Ingredient;
import com.example.kestubois.R;
import com.example.kestubois.Singleton;
import com.example.kestubois.classSimple.IngredientExtended;
import com.example.kestubois.classSimple.ListeDeCourses;

import java.util.ArrayList;
import java.util.HashMap;
//N'est plus utilisé car on utilise un recyclerView maintenant
public class PageListeCourses extends AppCompatActivity {

    ListView LISTE_achats;
    Button BTN_continuerSelection;
    Button BTN_effectuerAchat;
    Singleton singleton;
    ListeDeCourses listeDeCourses;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_layout);
        singleton = Singleton.getInstance();

        LISTE_achats = (ListView) findViewById(R.id.LISTE_achats);      // Récupération de l'ID de l'élément
        ArrayList<HashMap<String, String>> listeItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> uneStruc;   // Initialisation d'une structure à mettre dans la liste (appel du fichier ligne.xml)



        //On récupère la liste de course pour l'ajouter à la liste
        this.listeDeCourses = singleton.getListeDeCourses();

        for(Ingredient ingredient : listeDeCourses.getIngredients()){
            uneStruc = new HashMap<>();
            uneStruc.put("Ingredient",ingredient.getNom());
            uneStruc.put("Quantitée",String.valueOf(ingredient.getQuantite()));
            listeItem.add(uneStruc);
        }


        //J'ai essayé de changer le this.getBaseContext() en this et en ListeCourse.this + getApplicationContext()
        SimpleAdapter adaptateurListe = new SimpleAdapter(this.getBaseContext(), listeItem, R.layout.ligne_achat, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_achat, R.id.LBL_quantitee_achat});       // Création d'un adaptateur d'affichage dans la liste




        LISTE_achats.setAdapter(adaptateurListe);       // Application de l'adaptateur




        BTN_effectuerAchat = (Button) findViewById(R.id.BTN_effectuerAchat);
        BTN_effectuerAchat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(PageListeCourses.this, PageCarteMagasin.class);     // Déclaration d'une fenêtre (Intent)
                startActivity(intent);      // Ouverture de la fenêtre (l'activité)
            }
        });


        BTN_continuerSelection = (Button) findViewById(R.id.BTN_continuerSelection);
        BTN_continuerSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(PageListeCourses.this, PageAccueil.class);     // Déclaration d'une fenêtre (Intent)
                startActivity(intent);      // Ouverture de la fenêtre (l'activité)
            }
        });

        LISTE_achats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IngredientExtended ingredient = listeDeCourses.getIngredients().get(position);
                if(!ingredient.getIsQuote()){
                    ingredient.setIsQuote(true);
                    view.setBackgroundColor(0xFF4DD2AE);
                    count+=1;
                }
                //Ensuite au display faut rafficher en fonction du getQuote
                if(count == listeItem.size()){
                    //Faire une fenêtre d'alerte
                    Log.wtf("wtf" , "jai fini");
                }
            }
        });
    }
}