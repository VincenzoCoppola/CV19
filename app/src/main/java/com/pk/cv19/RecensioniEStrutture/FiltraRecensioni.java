package com.pk.cv19.RecensioniEStrutture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pk.cv19.R;

public class FiltraRecensioni extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private RecensioniEStrutture_Controller Controller = new RecensioniEStrutture_Controller(this);
    private RecyclerView lista_recensioni;
    String id_struttura;
    String filtro;
    private TextView tvFiltro;

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtra_recensioni);
        filtro = getIntent().getExtras().getString("Filtro");
        id_struttura = getIntent().getExtras().getString("Id_Struttura");
        lista_recensioni = findViewById(R.id.rvFiltrata);
        tvFiltro = findViewById(R.id.textView31);
        tvFiltro.setText(filtro);
        Controller.MostraRecensioniFiltrate(filtro,id_struttura,lista_recensioni);
    }

    /*-----Star&Stop listening-----*/
    @Override
    protected void onStart() {
        super.onStart();
        Controller.Start_Adapter2();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Controller.Stop_Adapter2();
    }

    /*---------------------------------*/

    //Pressione del tasto fisico back per tornare alla schermata precedente.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Controller.btnBack(id_struttura);
    }

    //Metodo per tornare alla struttura in dettaglio.
    public void tornaaStrutturaDettagli(View view) {
        Controller.btnBack(id_struttura);
    }
}