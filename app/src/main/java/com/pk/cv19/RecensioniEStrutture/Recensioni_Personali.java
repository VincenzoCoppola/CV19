package com.pk.cv19.RecensioniEStrutture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.pk.cv19.R;

public class Recensioni_Personali extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private RecensioniEStrutture_Controller Controller = new RecensioniEStrutture_Controller(this);
    private RecyclerView lista_recensioni;
    private String id_utente;

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensioni__personali);
        id_utente = getIntent().getExtras().getString("Id_Utente");
        lista_recensioni=findViewById(R.id.List_Recensioni_Personali);
        Controller.MostraRecensioniPersonali(lista_recensioni,id_utente);
    }

    /*-----Star&Stop listening-----*/
    @Override
    protected void onStop() {
        super.onStop();
        Controller.Stop_Adapter3();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Controller.Start_Adapter3();
    }
    /*-----------------------------*/

    //Tasti back.
    public void TornaMenù(View view) {
        Controller.btnMenuPersonale();
    }
    @Override
    public void onBackPressed() {
        Controller.btnMenuPersonale();
    }
    /*------------------------------*/
}