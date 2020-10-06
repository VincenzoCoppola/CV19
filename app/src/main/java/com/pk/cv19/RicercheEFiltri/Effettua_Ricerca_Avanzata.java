package com.pk.cv19.RicercheEFiltri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pk.cv19.R;

public class Effettua_Ricerca_Avanzata extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private EditText Città;
    private Spinner Tipo;
    private EditText Voto;
    double VOTO;
    private RicercheEFiltri_Controller Controller = new RicercheEFiltri_Controller(this);

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effettua_ricerca__avanzata);
        Città = findViewById(R.id.editTextTextPersonName9);
        Voto = findViewById(R.id.editTextTextPersonName11);
        Tipo=findViewById(R.id.spinner);
        setSpinner(Tipo);       //Richiamo metodo
    }

    //Metodo che setta lo spinner con i valori prestabiliti.
    private void setSpinner(Spinner tipo) {
        //Arrayadapter utilizzato per la gestione di insiemi di dati.
        ArrayAdapter<CharSequence> adapter_tipo = ArrayAdapter.createFromResource(this,R.array.defaultValue,R.layout.support_simple_spinner_dropdown_item);
        tipo.setAdapter(adapter_tipo);
    }

    //Tasto avvia ricerca.
    public void avviaRicercaAvanzata(View view) {
        //Recupero dati
        String tipo_query="";       //Inizialmente la query è vuota perchè non sappiamo ancora l'utente quale tasto ha premuto.
        String città = Città.getText().toString();
        String voto = Voto.getText().toString();
        String tipo = Tipo.getSelectedItem().toString();
        if (tipo.equals("Ristoranti")){
            tipo_query = "Ris";
        }
        else if(tipo.equals("Hotel")){
            tipo_query = "Hot";
        }
        else if(tipo.equals("Attrazioni")){
            tipo_query = "Att";
        }

        if (! voto.isEmpty() ){
            VOTO =Double.parseDouble(voto);
        }
        if (città.isEmpty() || voto.isEmpty() || tipo.equals("Seleziona il tipo")){
            Toast.makeText(this,"Uno o più campi vuoti!",Toast.LENGTH_LONG).show();
        }
        else if(VOTO < 1 || VOTO >5){
            Toast.makeText(this,"Voto compreso tra 1 e 5!",Toast.LENGTH_LONG).show();
        }
        else{
            Intent avanzata = new Intent(this, Ricerca_Avanzata.class);
            avanzata.putExtra("Tipo",tipo_query);
            avanzata.putExtra("Città",città);
            avanzata.putExtra("Voto",voto);
            startActivity(avanzata);
            finish();
        }
    }

    //Metodi per tornare alla schermata principale.
    public void TornaMain(View view) {
        Controller.btnMain();
    }

    @Override
    public void onBackPressed() {
        Controller.btnMain();
    }
    /*------------------------------------------------------*/
}