package com.example.kestubois.classVue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kestubois.classSimple.Cocktail;
import com.example.kestubois.InterfaceController;
import com.example.kestubois.JSONParser;
import com.example.kestubois.R;
import com.example.kestubois.Singleton;

import java.util.ArrayList;


public class PageAccueil extends AppCompatActivity implements InterfaceController {

    ListView mListView;
    Singleton singleton;
    String[] waitingTabCocktails = new String[]{"Pending..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialisation du Singleton
        Singleton.initInstance();
        //JSON parser permet de construire les objects cocktail à partir d'une requête http
        JSONParser jsonParser = new JSONParser(this, this);
        this.singleton = Singleton.getInstance();

        //Si JSONParser a déja fait le travail on va dans le onLoad(), sinon on bloque l'utilisateur avec une fausse liste d'attente
        if(!this.singleton.isLoaded()) {

            // Récupération de l'ID de l'élément et application de l'adapteur
            mListView = (ListView) findViewById(R.id.LISTE_cocktail);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PageAccueil.this, android.R.layout.simple_list_item_1, waitingTabCocktails);
            mListView.setAdapter(adapter);

        }else{
            onLoad();
        }
    }

    //Fonction onLoad provenant de notre interface (Est executé une fois que la fonction async de la requête http est effectuée)
    @Override
    public void onLoad() {
        //Récupération de tout les cocktails ajoutés au singleton
        ArrayList<Cocktail> cocktailsList = this.singleton.getCocktailList();

        //Récupération du nom de chaque coktail pour l'affichage dans la listView
        String[] tabCockTails2 = new String[cocktailsList.size()];
        for(int i = 0; i < cocktailsList.size(); i++){
            tabCockTails2[i] = cocktailsList.get(i).getNom();
        }

        mListView = (ListView) findViewById(R.id.LISTE_cocktail);       // Récupération de l'ID de l'élément
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PageAccueil.this, android.R.layout.simple_list_item_1, tabCockTails2);
        mListView.setAdapter(adapter);
        //On replalce des events sur chacun des items de la liste
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Bien joué", Toast.LENGTH_SHORT).show();
                // Déclaration d'un object qui récupère la position (l'élément de la liste)
                Object monCocktail = mListView.getItemAtPosition(position);
                // Déclaration d'une fenêtre (Intent)
                Intent intent = new Intent(PageAccueil.this, PageDetailCocktail.class);      // Passage en paramètre à retourner (le nom du cocktail (PutExtra))
                intent.putExtra("Position", String.valueOf(position));
                startActivity(intent);      // Ouverture de la fenêtre (l'activité)
                Toast.makeText(PageAccueil.this, "Cocktail : " + monCocktail, Toast.LENGTH_LONG).show();     //Affichage d'un petit message de durée courte, pour l'utilisateur
            }
        });
        //On dit au singleton que la liste est construite (pour ne pas refaire de requête à la bdd une autre fois
        //Lors d'un retour sur cette page
        this.singleton.setLoaded(true);
    }
}

