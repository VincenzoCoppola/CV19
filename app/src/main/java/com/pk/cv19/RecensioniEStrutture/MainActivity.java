package com.pk.cv19.RecensioniEStrutture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pk.cv19.Adapter.Adapter_Struttura;
import com.pk.cv19.R;

public class MainActivity extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private RecensioniEStrutture_Controller Controller = new RecensioniEStrutture_Controller(this);
    private RecyclerView mFirestoreList;
    private String Email;
    private EditText Ricerca_Nome;
    private Adapter_Struttura adapter;

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ricerca_Nome = findViewById(R.id.editTextTextPersonName13);
        mFirestoreList = findViewById(R.id.List_Strutture);
        Controller.Mostra_Strutture(mFirestoreList);
    }

    /*-----Star&Stop listening-----*/
    @Override
    protected void onStop() {
        super.onStop();
        Controller.Stop_Adapter();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Controller.Start_Adapter();
    }
    /*---------------------------------*/

    //Metodi per filtrare la ricerca.
    public void FiltraHotel(View view) {
        Controller.FiltraHotelPremuto();
    }
    public void FiltraRistoranti(View view) {
        Controller.FiltraRistorantiPremuto();
    }
    public void FiltraAttrazioni(View view) {
        Controller.FiltraAttrazioniPremuto();
    }
    /*--------------------------------------------------------*/

    //Metodo per la ricerca avanzata.
    public void Ricerca_Avanzata(View view) {
        Controller.Avanzata();
    }


    //Appena si preme il tasto menù, viene aperta una nuova activity (Menu_Personale).
    public void Apri_menuPersonale(View view) {
        Controller.Menu_Personale(Email);
    }

    //Se si preme il tasto fisico back, non si  potrà tornare all'activity precedente.
    @Override
    public void onBackPressed() {
       Controller.Back();
    }

    //Metodo collegato al tasto mappa.
    public void ApriMappa(View view) {
        Controller.Mostra_Mappa();
    }

    //Metodo collegato all'editText per la ricerca.
    public void Ricerca_per_Nome(View view) {
        String Nome = Ricerca_Nome.getText().toString();
        if (Nome.isEmpty()){    //Se non viene inserito nulla e si preme cerca uscirà un messaggio d'errore!.
            Toast.makeText(this,"Campo Vuoto!",Toast.LENGTH_LONG).show();
        }
        else if (Nome.length() <= 3){
            Toast.makeText(this,"Nome non valido, minimo 4 caratteri",Toast.LENGTH_LONG).show();
        }
        else{
            Controller.Nome_Ricerca(Nome);
        }
    }
}
