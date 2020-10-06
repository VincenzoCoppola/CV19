package com.pk.cv19.RecensioniEStrutture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pk.cv19.R;
import com.squareup.picasso.Picasso;

public class Mostra_Struttura_Dettaglio extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private RecensioniEStrutture_Controller Controller = new RecensioniEStrutture_Controller(this);
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = database.collection("Strutture");
    private TextView nome_struttura;
    private TextView descrizione_struttura;
    private ImageView immagine_struttura;
    private TextView indirizzo_struttura;
    private TextView telefono_struttura;
    private String id_struttura;
    private RatingBar ratingVal;
    private Spinner filtroStelle;
    private RecyclerView lista_recensioni;

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra__struttura__dettaglio);
        id_struttura= getIntent().getExtras().getString("id");
        filtroStelle =findViewById(R.id.spinner2);
        setSpinner(filtroStelle);
        lista_recensioni=findViewById(R.id.RVlista_recensioni);
        Controller.Mostra_Recensioni(lista_recensioni,id_struttura);
        mostra_struttura(id_struttura);
    }

    //Metodo che setta lo spinner con i valori prestabiliti.
    private void setSpinner(Spinner filtroStelle) {
        ArrayAdapter<CharSequence> adapter_tipo = ArrayAdapter.createFromResource(this,R.array.defaultStelle,R.layout.support_simple_spinner_dropdown_item);
        filtroStelle.setAdapter(adapter_tipo);
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
    /*-----------------------------------*/


    //Se viene premuto il tasto back (fisico), si ritorna all'activity main.
    @Override
    public void onBackPressed() {
        Controller.btnMain();
    }


    //Metodo che si occupa di recuperare tutti i dati di una struttura.
    private void mostra_struttura(String id) {
        nome_struttura = findViewById(R.id.textViewNome);
        descrizione_struttura = findViewById(R.id.textViewDescrizione);
        immagine_struttura = findViewById(R.id.imageView);
        indirizzo_struttura = findViewById(R.id.textViewIndirizzo);
        telefono_struttura = findViewById(R.id.textView30);
        ratingVal = findViewById(R.id.ratingBar2);
        final DocumentReference dati = notebookRef.document(id);
        dati.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        nome_struttura.setText(document.getString("Nome"));
                        descrizione_struttura.setText(document.getString("Descrizione"));
                        Picasso.get().load(document.getString("Immagine")).into(immagine_struttura);
                        indirizzo_struttura.setText(document.getString("Indirizzo"));
                        telefono_struttura.setText(document.getString("Telefono"));
                        ratingVal.setRating(Float.parseFloat(String.valueOf(document.getDouble("Valutazione"))));
                    }
                }
            }
        });
    }


    //Appena si preme il tasto, si crea una nuova activity che si occupa di gestire l'inserimento di una recensione.
    public void ScriviUnaRecensione(View view) {
        Controller.Scrivi_Recensione(id_struttura);
    }

    //Metodo per tornare alla schermata principale.
    public void TornaMain(View view) {
        Controller.btnMain();
    }

    //Metodo per settare tramite spinner il filtraggio delle recensioni.
    public void btnFiltraPressed(View view) {
        String filtro = filtroStelle.getSelectedItem().toString();
        if (filtro.equals("Top recensioni")){
            Toast.makeText(this,"Filtro gia attivo", Toast.LENGTH_LONG).show();
        }
        else{
            Controller.Filtra_Recensioni(filtro,id_struttura);
        }

    }

}