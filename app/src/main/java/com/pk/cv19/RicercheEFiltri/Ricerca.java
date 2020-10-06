package com.pk.cv19.RicercheEFiltri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pk.cv19.R;

public class Ricerca extends AppCompatActivity {
    /*-----Dichiarazioni-----*/
    private RicercheEFiltri_Controller Controller = new RicercheEFiltri_Controller(this);
    private String tipoC;
    private String Nome;
    private RecyclerView lista;
    private TextView etCategoria;

    /*-----Metodi-----*/
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ricerca);
            lista = findViewById(R.id.List_Ricerca);
            tipoC = getIntent().getExtras().getString("TipoC");
            Nome = getIntent().getExtras().getString("Nome");
            etCategoria = findViewById(R.id.editTextTextPersonName10);

            //Controllo delle scelte.
            if (Nome == null) {     //In questo caso, l'utente avrà premuto su uno dei primi 3 tasti.
                if (tipoC.equals("Ris")) {
                    etCategoria.setText("Ristoranti");
                } else if (tipoC.equals("Hot")) {
                    etCategoria.setText("Hotel");
                } else if (tipoC.equals("Att")) {
                    etCategoria.setText("Attrazioni");
                }
                Controller.Mostra_Struttura_Tipologia(tipoC, lista);
            }else if (tipoC == null) {       //Altrimenti avrà cercato per nome.
                etCategoria.setText(Nome);
                Controller.Mostra_Struttura(Nome, lista);
                }

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
        /*--------------------------------*/

        //Tasti back.
        public void TornaMain2(View view) {
            Controller.btnMain();
        }
        @Override
        public void onBackPressed() {
            Controller.btnMain();
        }
        /*----------------------------------*/
    }

