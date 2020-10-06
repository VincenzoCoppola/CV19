package com.pk.cv19.RicercheEFiltri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pk.cv19.R;

public class Ricerca_Avanzata extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private String Tipo;
    private String Città;
    private String Voto;
    private double VOTO;
    private TextView etTipo;
    private TextView etCittà;
    private TextView etVoto;
    private RecyclerView lista;
    private RicercheEFiltri_Controller Controller = new RicercheEFiltri_Controller(this);

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca__avanzata);
        //Recupero variabili dall'activity
        lista = findViewById(R.id.List_Ricerca_Avanzata);
        Tipo = getIntent().getExtras().getString("Tipo");
        Città = getIntent().getExtras().getString("Città");
        Voto = getIntent().getExtras().getString("Voto");
        etCittà=findViewById(R.id.editTextTextPersonName5);
        etVoto=findViewById(R.id.editTextTextPersonName8);
        etTipo=findViewById(R.id.editTextTextPersonName6);
        etCittà.setText(Città);
        VOTO=Double.parseDouble(Voto);
        etVoto.setText( Voto);
        //Controllo sul tipo
        if (Tipo.equals("Ris")){
            etTipo.setText("Ristoranti");
        }
        else if(Tipo.equals("Hot")){
            etTipo.setText("Hotel");
        }
        else if(Tipo.equals("Att")){
            etTipo.setText("Attrazioni");
        }
        Controller.Struttura_Avanzata(Tipo,Città,VOTO,lista);
    }

    /*-----Star&Stop listening-----*/
    @Override
    protected void onStart() {
        super.onStart();
        Controller.Start_Adapter();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Controller.Stop_Adapter();
    }
    /*----------------------------*/

    //Gestione del tasto back.
    public void TornaMain2(View view) {
        Controller.btnMain();
    }
    @Override
    public void onBackPressed() {
        Controller.btnMain();
    }
    /*------------------------------*/
}