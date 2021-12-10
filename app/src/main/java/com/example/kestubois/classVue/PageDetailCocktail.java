package com.example.kestubois.classVue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kestubois.classSimple.Cocktail;
import com.example.kestubois.classSimple.Ingredient;
import com.example.kestubois.R;
import com.example.kestubois.Singleton;
import com.example.kestubois.classSimple.ListeDeCourses;
import com.example.kestubois.recyclerView.InGredientListActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class PageDetailCocktail extends AppCompatActivity {


    private Button BTN_retour;
    private Button BTN_ajouter;
    private Button BTN_plus;
    private Button BTN_moins;
    private ListView LISTE_ingredient;
    private TextView LBL_nomCocktail;
    private TextView TEXT_nbr_pers;
    private EditText Description;
    private int quant = 1;
    private String uneQuant = String.valueOf(quant);
    private Cocktail cocktailActuel;
    private Singleton singleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_cocktail);
        Intent intent = this.getIntent();

        TEXT_nbr_pers = (TextView) findViewById(R.id.TEXT_nbr_pers);
        TEXT_nbr_pers.setText(uneQuant);

        //L'index de là ou on a cliqué dans la liste
        int position = Integer.parseInt(intent.getStringExtra("Position"));

        //Déclaration des différents élèments dont nous auront besoin pour construire cette page
        this.singleton = Singleton.getInstance();
        this.cocktailActuel = singleton.getCocktailList().get(position);
        String nomCocktail = this.cocktailActuel.getNom();
        String description = this.cocktailActuel.getDescription();
        ArrayList<Ingredient> cocktailActuelIngredients = this.cocktailActuel.getIngredients();

        //Ajout des informations de nom de cocktail et description
        Description = (EditText) findViewById(R.id.TBX_multiLine_description);
        Description.setText(description);
        LBL_nomCocktail = (TextView)findViewById(R.id.LBL_nomCocktail);
        LBL_nomCocktail.setText(nomCocktail);


        LISTE_ingredient = (ListView) findViewById(R.id.LISTE_ingredient);      // Récupération de l'ID de l'élément

        ArrayList<HashMap<String, String>> listeIngredients = new ArrayList<HashMap<String, String>>();
        // Initialisation d'une structure à mettre dans la liste (appel du fichier ligne.xml)
        HashMap<String, String> uneStruc;

        //Récupération des ingrédients du cocktail
        for(int i = 0; i < cocktailActuelIngredients.size(); i++){
            uneStruc = new HashMap<String, String>();
            uneStruc.put("Ingredient",cocktailActuelIngredients.get(i).getNom());
            uneStruc.put("Quantitée",Float.toString(cocktailActuelIngredients.get(i).getQuantite()));
            listeIngredients.add(uneStruc);
        }

        SimpleAdapter adaptateurListe = new SimpleAdapter(this.getBaseContext(), listeIngredients, R.layout.ligne, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_liste, R.id.LBL_quantitee_liste});       // Création d'un adaptateur d'affichage dans la liste
        LISTE_ingredient.setAdapter(adaptateurListe);

        BTN_retour = (Button) findViewById(R.id.BTN_retour);
        BTN_retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PageAccueil.class);
                startActivity(myIntent);
            }
        });

        BTN_ajouter = (Button) findViewById(R.id.BTN_ajouter);
        BTN_ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //C'est là qu'on va ajouter les ingrédients a la liste de course

                //Init de la liste de course
                ListeDeCourses listeDeCourses = singleton.getListeDeCourses();

                //Ajout des ingrédients du cocktail actual avec les bonnes quantités à la liste de course
                for(Ingredient ingredient : cocktailActuel.getIngredients()) {
                    listeDeCourses.addIngredient(ingredient.getNom(), ingredient.getQuantite() * quant);
                }

                Intent myIntent = new Intent(view.getContext(), InGredientListActivity.class);    // Déclaration d'une fenêtre (Intent)
                startActivity(myIntent);        // Ouverture de la fenêtre (l'activité)

            }
        });


        BTN_plus = (Button) findViewById(R.id.BTN_plus);
        BTN_moins = (Button) findViewById(R.id.BTN_moins);

        BTN_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                quant = quant +1;
                uneQuant = String.valueOf(quant);
                TEXT_nbr_pers.setText(uneQuant);
                if (quant == 100){
                    Toast.makeText(PageDetailCocktail.this, "Le nombre de personne est trop gros, bande d'alcoolique !", Toast.LENGTH_LONG).show();
                    quant = 99;
                    uneQuant = String.valueOf(quant);
                    TEXT_nbr_pers.setText(uneQuant);
                }
                updateQuantitee(quant);
            }
        });

        BTN_moins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                quant = quant -1;
                uneQuant = String.valueOf(quant);
                TEXT_nbr_pers.setText(uneQuant);
                if (quant == 0){
                    Toast.makeText(PageDetailCocktail.this, "Le nombre de personne est trop bas, tu vas pas faire un cocktail pour casper !", Toast.LENGTH_LONG).show();
                    quant = 1;
                    uneQuant = String.valueOf(quant);
                    TEXT_nbr_pers.setText(uneQuant);
                }
                updateQuantitee(quant);
            }
        });
    }

    public void updateQuantitee(int quant){
        ArrayList<Ingredient> ingredients = cocktailActuel.getIngredients();
        ArrayList<HashMap<String,String>> updatedQuant = new ArrayList<>();
        HashMap<String,String> hashMapIngredients = new HashMap<>();

        for(Ingredient ingredient : ingredients){
            hashMapIngredients = new HashMap<>();
            hashMapIngredients.put("Ingredient",ingredient.getNom());
            hashMapIngredients.put("Quantitée",String.valueOf(ingredient.getQuantite() * quant));
            updatedQuant.add(hashMapIngredients);
        }

        SimpleAdapter adapteurListNew = new SimpleAdapter(PageDetailCocktail.this, updatedQuant, R.layout.ligne, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_liste, R.id.LBL_quantitee_liste});
        LISTE_ingredient.setAdapter(adapteurListNew);

    }
}