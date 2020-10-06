package com.pk.cv19.RicercheEFiltri;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pk.cv19.Adapter.Adapter_Struttura;
import com.pk.cv19.Modelli.Modello_Struttura;
import com.pk.cv19.RecensioniEStrutture.MainActivity;
import com.pk.cv19.RecensioniEStrutture.Mostra_Struttura_Dettaglio;

public class RicercheEFiltri_Controller {

    /*-----Dichiarazioni-----*/
    private final AppCompatActivity Context;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference= database.collection("Strutture");
    private RecyclerView lista;
    private Adapter_Struttura adapter;

    /*-----Metodi-----*/

    //Metodo che setta il contesto.
    public RicercheEFiltri_Controller(AppCompatActivity Activity){
        Context = Activity;
    }

    //Metodo che ricerca le strutture in base ai criteri di ricerca avanzati.
    public void Struttura_Avanzata(String tipo, String città, double voto, RecyclerView lista_per_avanzata) {
        Query query =collectionReference.whereEqualTo("Tipo", tipo).whereGreaterThanOrEqualTo("Valutazione",voto).whereEqualTo("Città",città).orderBy("Valutazione",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Modello_Struttura> options = new FirestoreRecyclerOptions.Builder<Modello_Struttura>().setQuery(query, Modello_Struttura.class).build();
        adapter = new Adapter_Struttura(options);       //Adapter per la gestione della lista
        adapter.setOnItemClickListner(new Adapter_Struttura.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                String id_struttura = docSnapshot.getId();
                Struttura_selezionata(id_struttura);
            }
        });
        lista_per_avanzata.setHasFixedSize(true);
        lista_per_avanzata.setLayoutManager(new LinearLayoutManager(Context));
        lista_per_avanzata.setAdapter(adapter);
    }

    //Metodo che recupera l'id della struttura selezionata e passa a schermata di
    //struttura in dettaglio.
    public void Struttura_selezionata(String id_struttura) {
        Intent mostra_struttura = new Intent(Context, Mostra_Struttura_Dettaglio.class);
        mostra_struttura.putExtra("id",id_struttura);
        Context.startActivity(mostra_struttura);
        Context.finish();
    }

    /*-----Star&Stop listening-----*/
    public void Start_Adapter() {
        adapter.startListening();
    }

    public void Stop_Adapter() {
        adapter.stopListening();
    }
    /*---------------------------------*/


    //Metodo per passare alla schermata principale.
    public void btnMain() {
        Intent  esci = new Intent(Context, MainActivity.class);
        Context.startActivity(esci);
        Context.finish();
    }

    //Metodo che apre la schermata di una determinata struttura in dettaglio.
    public void Mostra_Struttura(String nome, RecyclerView lista_nome) {
            StringBuilder b = new StringBuilder(nome.toLowerCase());
            int i = 0;
            do {
                b.replace(i, i + 1, b.substring(i, i + 1).toUpperCase());
                i = b.indexOf(" ", i) + 1;
            } while (i > 0 && i < b.length());
            String NOME = b.toString();
            String prefisso;
            prefisso = (String) NOME.subSequence(0, 4);
            Query query = collectionReference.orderBy("Nome").startAt(prefisso).endAt(prefisso + "\uf8ff");
            FirestoreRecyclerOptions<Modello_Struttura> options = new FirestoreRecyclerOptions.Builder<Modello_Struttura>().setQuery(query, Modello_Struttura.class).build();
            adapter = new Adapter_Struttura(options);       //Adapter per la gestione dell'elenco.
            adapter.setOnItemClickListner(new Adapter_Struttura.OnItemClickListner() {
                @Override
                public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                    String id_struttura = docSnapshot.getId();
                    Struttura_selezionata(id_struttura);        //Richiamo metodo.
                }
            });
            lista_nome.setHasFixedSize(true);
            lista_nome.setLayoutManager(new LinearLayoutManager(Context));
            lista_nome.setAdapter(adapter);
    }

    //Metodo che ricerca le strutture in base ai criteri di ricerca filtrata per tipo.
    public void Mostra_Struttura_Tipologia(String tipo, RecyclerView lista_per_categoria) {
        //Query per il recupero delle strutture in base alla tipologia scelta
        Query query =collectionReference.whereEqualTo("Tipo", tipo).orderBy("Valutazione", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Modello_Struttura> options = new FirestoreRecyclerOptions.Builder<Modello_Struttura>().setQuery(query, Modello_Struttura.class).build();
        adapter = new Adapter_Struttura(options);       //Adapter per la gestione dell'elenco.
        adapter.setOnItemClickListner(new Adapter_Struttura.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                String id_struttura = docSnapshot.getId();
                Struttura_selezionata(id_struttura);        //Richiamo metodo.
            }
        });
        lista_per_categoria.setHasFixedSize(true);
        lista_per_categoria.setLayoutManager(new LinearLayoutManager(Context));
        lista_per_categoria.setAdapter(adapter);
    }

}
