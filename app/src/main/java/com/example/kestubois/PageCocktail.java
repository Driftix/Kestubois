package com.example.kestubois;

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

import java.util.ArrayList;
import java.util.HashMap;

public class PageCocktail extends AppCompatActivity {

    //************************  Déclaration des éléments ***********************//
    Button BTN_retour;
    Button BTN_ajouter;
    Button BTN_plus;
    Button BTN_moins;
    ListView LISTE_ingredient;
    TextView LBL_nomCocktail;
    TextView TEXT_nbr_pers;
    EditText Description;
    int quant = 1;
    String uneQuant = String.valueOf(quant);
    Cocktail cocktailActuel;

    Singleton singleton;
    //************************  Fin ********************************************//

   // //************************  Déclaration des données à afficher ***********************//
   // String[] TabIngredient = new String[]{
   //         "Citron Vert : 2", "Sucre : 50g", "Rhum : 5cl", "Menthe : +/- 5 feuilles"
   // };
   // //************************  Fin ********************************************//

    //************************  Initialisation de la fenêtre ***********************//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_cocktail);

        //---------------------Guilhem-------------------//
        this.singleton = Singleton.getInstance();

        ArrayList<Cocktail> cocktailsList = this.singleton.getCocktailList();


        //************************  Gestion du nombre de personnes ***********************//
        TEXT_nbr_pers = (TextView) findViewById(R.id.TEXT_nbr_pers);
        TEXT_nbr_pers.setText(uneQuant);

        //************************  Récupération du nom du cocktail ***********************//
        //------------------------Récupération des éléments liés au cocktails cliqué
        Intent intent = this.getIntent();       // Récupération de l'intent
        //L'index de là ou on a cliqué dans la liste
        int position = Integer.parseInt(intent.getStringExtra("Position"));

        this.cocktailActuel = singleton.getCocktailList().get(position);
        String nomCocktail = this.cocktailActuel.getNom();
        String description = this.cocktailActuel.getDescription();
        
        Description = (EditText) findViewById(R.id.TBX_multiLine_description);

        Description.setText(description);
        LBL_nomCocktail = (TextView)findViewById(R.id.LBL_nomCocktail);     // Récupération de l'ID de l'élément
        //String NomDeCocktail= intent.getStringExtra("NomDeCocktail");   // Récupération du paramètre au nom déclarer dans l'intent précédent
        LBL_nomCocktail.setText(nomCocktail);     //Remplacement du titre



        //************************  Déclaration des éléments dans une liste personnalisée ***********************//
        LISTE_ingredient = (ListView) findViewById(R.id.LISTE_ingredient);      // Récupération de l'ID de l'élément

        ArrayList<HashMap<String, String>> listeIngredients = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> uneStruc;  // Initialisation d'une structure à mettre dans la liste (appel du fichier ligne.xml)

        //Récupération des ingrédients du cocktail
        for(int i = 0; i < cocktailsList.get(position).getIngredients().size(); i++){
            uneStruc = new HashMap<String, String>();
            uneStruc.put("Ingredient",cocktailsList.get(position).getIngredients().get(i).getNom());
            System.out.println(cocktailsList.get(position).getIngredients().get(i).getNom());
            uneStruc.put("Quantitée",Float.toString(cocktailsList.get(position).getIngredients().get(i).getQuantite()));
            System.out.println(Float.toString(cocktailsList.get(position).getIngredients().get(i).getQuantite()));
            listeIngredients.add(uneStruc);
        }

        SimpleAdapter adaptateurListe = new SimpleAdapter(this.getBaseContext(), listeIngredients, R.layout.ligne, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_liste, R.id.LBL_quantitee_liste});       // Création d'un adaptateur d'affichage dans la liste
        LISTE_ingredient.setAdapter(adaptateurListe);       // Application de l'adaptateur




        //************************  Ajout d'une action à un bouton ***********************//
        BTN_retour = (Button) findViewById(R.id.BTN_retour);
        BTN_retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });
        //************************  Fin ********************************************//

        //************************  Aout d'une action à un bouton ***********************//
        BTN_ajouter = (Button) findViewById(R.id.BTN_ajouter);      // Récupération de l'ID de l'élément
        BTN_ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //C'est là qu'on va ajouter les ingrédients a la liste de course

                //Init de la liste de course
                ListeDeCourses listeDeCourses = singleton.getListeDeCourses();


                for(Ingredient ingredient : cocktailActuel.getIngredients()) {
                    System.out.println("on passe dans le truc cool");
                    listeDeCourses.addIngredient(ingredient.getNom(), ingredient.getQuantite() * quant);
                }

                Intent myIntent = new Intent(view.getContext(), ListeCourses.class);    // Déclaration d'une fenêtre (Intent)
                startActivity(myIntent);        // Ouverture de la fenêtre (l'activité)

            }
        });
        //************************  Fin ********************************************//


        //************************ Gestion des boutons + / - personnes ***********************//
        BTN_plus = (Button) findViewById(R.id.BTN_plus);
        BTN_moins = (Button) findViewById(R.id.BTN_moins);

        BTN_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                quant = quant +1;
                uneQuant = String.valueOf(quant);
                TEXT_nbr_pers.setText(uneQuant);
                if (quant == 100){
                    Toast.makeText(PageCocktail.this, "Le nombre de personne est trop gros, bande d'alcoolique !", Toast.LENGTH_LONG).show();
                    quant = 99;
                    uneQuant = String.valueOf(quant);
                    TEXT_nbr_pers.setText(uneQuant);
                }

                updateQuantitee(quant);
                /*
                int quantite = quant;
                float test=  0;
                ArrayList<HashMap<String, String>> arrayNewDisplay = new ArrayList<>();
                HashMap<String, String> copieHashMap = new HashMap<>();

                for(HashMap<String, String> hm : listeIngredients){
                    for(Map.Entry mapentry : hm.entrySet()){
                        copieHashMap.put(mapentry.getKey().toString(),mapentry.getValue().toString());
                        if(isParsable(mapentry.getValue().toString())){
                            test = Float.parseFloat(mapentry.getValue().toString()) * quantite;
                            copieHashMap.put(mapentry.getKey().toString(), String.valueOf(test));
                        }
                    }
                    arrayNewDisplay.add(copieHashMap);
                }
                SimpleAdapter adapteurListNew = new SimpleAdapter(PageCocktail.this, arrayNewDisplay, R.layout.ligne, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_liste, R.id.LBL_quantitee_liste});
                LISTE_ingredient.setAdapter(adapteurListNew);
                */
            }
        });
        BTN_moins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                quant = quant -1;
                uneQuant = String.valueOf(quant);
                TEXT_nbr_pers.setText(uneQuant);
                if (quant == 0){
                    Toast.makeText(PageCocktail.this, "Le nombre de personne est trop bas, tu vas pas faire un cocktail pour casper !", Toast.LENGTH_LONG).show();
                    quant = 1;
                    uneQuant = String.valueOf(quant);
                    TEXT_nbr_pers.setText(uneQuant);
                }


                //Bon ben je suis completement explosé mais ça marche Oo
                //Fais en étant dans un état second donc aucune idée de si ça marche

                updateQuantitee(quant);
            /*
                int quantite = quant;
                float test = 0;

                ArrayList<HashMap<String, String>> arrayNewDisplay = new ArrayList<>();
                HashMap<String, String> copieHashMap = new HashMap<>();
                //Faut récupérer les hashmap du tableau d'avant (arrayNewDisplay)
                for(HashMap<String, String> hm : arrayNewDisplay){
                    for(Map.Entry mapentry : hm.entrySet()){
                        copieHashMap.put(mapentry.getKey().toString(),mapentry.getValue().toString());
                        if(isParsable(mapentry.getValue().toString())){
                            test = Float.parseFloat(mapentry.getValue().toString()) / quantite;
                            copieHashMap.put(mapentry.getKey().toString(), String.valueOf(test));
                        }
                    }
                    arrayNewDisplay.add(copieHashMap);
                }
                SimpleAdapter adapteurListNew = new SimpleAdapter(PageCocktail.this, arrayNewDisplay, R.layout.ligne, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_liste, R.id.LBL_quantitee_liste});
                LISTE_ingredient.setAdapter(adapteurListNew);
            */
            }
        });

        //************************  Fin ********************************************//
    }
    public boolean isParsable(String str){
        if(str == null){
            return false;
        }
        try{
            float f = Float.parseFloat(str);
        }catch(NumberFormatException e ){
            return false;
        }
        return true;
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

        SimpleAdapter adapteurListNew = new SimpleAdapter(PageCocktail.this, updatedQuant, R.layout.ligne, new String[]{"Ingredient", "Quantitée"}, new int[]{R.id.LBL_ingredient_liste, R.id.LBL_quantitee_liste});
        LISTE_ingredient.setAdapter(adapteurListNew);

    }
}