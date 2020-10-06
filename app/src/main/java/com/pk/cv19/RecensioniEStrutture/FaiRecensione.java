package com.pk.cv19.RecensioniEStrutture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pk.cv19.R;

public class FaiRecensione extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private RecensioniEStrutture_Controller Controller = new RecensioniEStrutture_Controller(this);
    private String Id_struttura;
    private EditText testo_recensione;
    private EditText titolo_recensione;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RatingBar rBar;

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fai_recensione);
        Id_struttura = getIntent().getExtras().getString("Id_Struttura");
        rBar = findViewById(R.id.ratingBar);
    }

    //Metodo che recupera tutti i campi necessari per una recensione.
    public void PubblicaRecensione(View view) {
        //Recupero dati dai vari campi.
        testo_recensione = findViewById(R.id.editTextTextPersonName);
        titolo_recensione = findViewById(R.id.editTextTextPersonName12);
        String getrating = String.valueOf(rBar.getRating());
        String Testo = testo_recensione.getText().toString();
        String Titolo= titolo_recensione.getText().toString();
        String id_autore = mAuth.getCurrentUser().getUid();

        //Controllo.
        if (Testo.isEmpty() || Titolo.isEmpty()) {
            Toast.makeText(this, "Uno o più campi sono vuoti.", Toast.LENGTH_LONG).show();
        }
        else if (Testo.length() < 10 || Titolo.length() < 4 || getrating.isEmpty()) {
            if (Testo.length() < 10) {
                Toast.makeText(this, "Recensione: minimo 10 caratteri", Toast.LENGTH_LONG).show();
            }
            if (Titolo.length() < 4){
                Toast.makeText(this, "Titolo: minimo 10 caratteri", Toast.LENGTH_LONG).show();
            }
            if (getrating.isEmpty()) {
                Toast.makeText(this,"Voto: inserire una votazione", Toast.LENGTH_LONG).show();
            }
        } else {

            //Se i controlli sono andati a buon fine allora inserisce tutto nel database.
           Controller.Fai_recensione(Testo,getrating,id_autore,Id_struttura,Titolo);
        }
    }

    //Metodi tasti back.
    @Override
    public void onBackPressed() {
        Controller.btnBack(Id_struttura);
    }

    public void TornaStrDet(View view) {
        Controller.btnBack(Id_struttura);
    }
    /*------------------------------------------*/
}
