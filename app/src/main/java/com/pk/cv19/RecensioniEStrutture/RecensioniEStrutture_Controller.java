package com.pk.cv19.RecensioniEStrutture;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pk.cv19.Adapter.Adapter_Struttura;
import com.pk.cv19.Adapter.Adapter_recensioniPersonali;
import com.pk.cv19.Adapter.Adapter_recensioniStruttura;
import com.pk.cv19.MappaESplash.Mappa;
import com.pk.cv19.Menù.Menu_Ospite;
import com.pk.cv19.Menù.Menu_Personale;
import com.pk.cv19.Modelli.Modello_Recensione;
import com.pk.cv19.Modelli.Modello_Struttura;
import com.pk.cv19.RicercheEFiltri.Effettua_Ricerca_Avanzata;
import com.pk.cv19.RicercheEFiltri.Ricerca;

import java.util.HashMap;
import java.util.Map;

public class RecensioniEStrutture_Controller {

    /*-----Dichiarazioni-----*/
    private final AppCompatActivity Context;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference notebookRef = firebaseFirestore.collection("Strutture");
    private Adapter_Struttura adapter;
    private Adapter_recensioniStruttura adapter2;
    private Adapter_recensioniPersonali adapter3;
    private Query recensioni;
    private double voti = 0;
    private int count = 0;
    private double totale = 0;
    private double media = 0;

    /*-----Metodi-----*/

    //Metodo che setta il contesto.
    public RecensioniEStrutture_Controller(AppCompatActivity Activity){
        Context = Activity;
    }

    //Metodo che mostra nella RecyclerView tutte le strutture ordinate in base alla valutazione.
    public void Mostra_Strutture(RecyclerView mFirestoreList) {
        //query per il recupero delle strutture nel database
        Query query = firebaseFirestore.collection("Strutture").orderBy("Valutazione", Query.Direction.DESCENDING );

        //RecyclerOption, usata per lo scorrimento delle strutture.
        FirestoreRecyclerOptions<Modello_Struttura> options = new FirestoreRecyclerOptions.Builder<Modello_Struttura>()
                .setQuery(query,Modello_Struttura.class).build();

        adapter = new Adapter_Struttura(options);   //Adapter utilizzato per la gestione dell'elenco.
        adapter.setOnItemClickListner(new Adapter_Struttura.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                String id_struttura = docSnapshot.getId();
                Selezione_Struttura(id_struttura);
            }
        });

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(Context));
        mFirestoreList.setAdapter(adapter);
    }

    //Metodo per passare alla schermata dettagli e recensioni di una struttura.
    public void Selezione_Struttura(String id_struttura) {
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

    public void Start_Adapter2() {
        adapter2.startListening();
    }

    public void Stop_Adapter2() {
        adapter2.stopListening();
    }

    public void Start_Adapter3() {
        adapter3.startListening();
    }

    public void Stop_Adapter3() {
        adapter3.stopListening();
    }

    /*---------------------------------*/

    //Filtraggio per tipo: Hotel.
    public void FiltraHotelPremuto() {
        Intent Hotel = new Intent(Context, Ricerca.class);
        Hotel.putExtra("TipoC","Hot");
        Context.startActivity(Hotel);
        Context.finish();
    }

    //Filtraggio per tipo: Ristoranti.
    public void FiltraRistorantiPremuto() {
        Intent Ristoranti = new Intent(Context, Ricerca.class);
        Ristoranti.putExtra("TipoC","Ris");
        Context.startActivity(Ristoranti);
        Context.finish();
    }

    //Filtraggio per tipo: Attrazioni.
    public void FiltraAttrazioniPremuto() {
        Intent Attrazioni = new Intent(Context, Ricerca.class);
        Attrazioni.putExtra("TipoC","Att");
        Context.startActivity(Attrazioni);
        Context.finish();
    }

    //Metodo per passare alla schermata di ricerca avanzata.
    public void Avanzata() {
        Intent ricerca_avanzata= new Intent(Context, Effettua_Ricerca_Avanzata.class);
        Context.startActivity(ricerca_avanzata);
        Context.finish();
    }

    //Metodo per passare alla schermata di menù personale dell'utente.
    public void Menu_Personale(String Email) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent menù = new Intent(Context, Menu_Personale.class);
        if (user!=null) {   //Se l'utente è registrato
            menù.putExtra("Email",Email);
            Context.startActivity(menù);
            Context.finish();
        }
        else {
            //Questo else viene eseguito nel caso si accede come ospite.
            Intent menùOspite = new Intent(Context, Menu_Ospite.class);
            Context.startActivity(menùOspite);
            Context.finish();
        }
    }

    //Metodo per passare alla schermata della mappa.
    public void Mostra_Mappa() {
        Intent mappa = new Intent(Context, Mappa.class);
        Context.startActivity(mappa);
        Context.finish();
    }

    //Metodo per passare alla schermata di ricerca per nome.
    public void Nome_Ricerca(String Nome) {
        Intent ricerca = new Intent(Context,Ricerca.class);
        ricerca.putExtra("Nome",Nome);
        Context.startActivity(ricerca);
        Context.finish();
    }

    //Metodo per visualizzare tutte le recensioni di una struttura.
    public void Mostra_Recensioni(RecyclerView lista_recensioni, String id_struttura) {
        recensioni = firebaseFirestore.collection("Recensione").whereEqualTo("Id_Struttura",id_struttura).orderBy("Voto",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Modello_Recensione> options = new FirestoreRecyclerOptions.Builder<Modello_Recensione>().setQuery(recensioni,Modello_Recensione.class).build();

        adapter2 = new Adapter_recensioniStruttura(options); //Adapter per l'elenco delle recensioni.
        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
        lista_recensioni.setAdapter(adapter2);
    }

    //Metodo per pubblicare una nuova recensione su una struttura.
    public void Scrivi_Recensione(String id_struttura) {
        Intent passaaRecensione = new Intent(Context,FaiRecensione.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            passaaRecensione.putExtra("Id_Struttura", id_struttura);
            Context.startActivity(passaaRecensione);
            Context.finish();
        }
        else{
            //Se nel caso hai eseguito l'accesso come ospite, non puoi effettuare recensioni.
            Toast.makeText(Context,"Devi essere loggato per fare una recensione", Toast.LENGTH_LONG).show();
        }
    }

    //Metodo per tornare alla schermata principale.
    public void btnMain() {
        Intent back = new Intent(Context,MainActivity.class);
        Context.startActivity(back);
        Context.finish();
    }

    public void Back(){
        new AlertDialog.Builder((android.content.Context) Context)
                .setTitle("Chiudi App")
                .setMessage("Sei sicuro di voler uscire dall'applicazione?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)Context).finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    //Metodo per filtrare le recensioni in base al numero di stelle.
    public void Filtra_Recensioni(String filtro, String id_struttura) {
        Intent intent = new Intent(Context,FiltraRecensioni.class);
        intent.putExtra("Filtro",filtro);
        intent.putExtra("Id_Struttura",id_struttura);
        Context.startActivity(intent);
        Context.finish();
    }

    //Metodo che carica la recensione e aggiorna la valutazione di una struttura nel database.
    public void Fai_recensione(String Testo, String getrating, String id_autore, final String Id_struttura, String Titolo) {
        //Se i controlli sono andati a buon fine allora inserisce tutto nel database
        Map<String, Object> nuova_recensione = new HashMap<>();
        nuova_recensione.put("Testo", Testo);
        nuova_recensione.put("Voto", getrating);
        nuova_recensione.put("Id_Autore", id_autore);
        nuova_recensione.put("Id_Struttura", Id_struttura);
        nuova_recensione.put("Titolo",Titolo);

        //Metodi di firebase.
        firebaseFirestore.collection("Strutture").document(Id_struttura).update("Valutazione",0);
        firebaseFirestore.collection("Recensione").add(nuova_recensione)     //In caso di successo la recensione viene pubblicata.
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Context, "Recensione pubblicata", Toast.LENGTH_SHORT).show();
                        Log.d("Recensione", "DocumentSnapshot added with ID: " + documentReference.getId());
                        firebaseFirestore.collection("Recensione").whereEqualTo("Id_Struttura", Id_struttura)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Log.i("Recension", "Tutto ok");
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                //Calcola la media dei voti
                                                String Voto = (String) document.get("Voto");
                                                voti = Double.parseDouble(String.valueOf(Voto));
                                                totale += voti;
                                                count++;
                                            }
                                            media = totale / count;
                                            notebookRef.document(Id_struttura).update("Valutazione", media);
                                            Intent esci = new Intent(Context, Mostra_Struttura_Dettaglio.class);
                                            esci.putExtra("id", Id_struttura);
                                            Context.startActivity(esci);
                                            Context.finish();
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //In caso di fallimento, messaggio d'errore.
                        Toast.makeText(Context, "Errore", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Metodo per tornare alla schermata di struttura in dettaglio.
    public void btnBack(String Id_struttura) {
        Intent back = new Intent(Context,Mostra_Struttura_Dettaglio.class);
        back.putExtra("id",Id_struttura);
        Context.startActivity(back);
        Context.finish();
    }

    //Metodo per mostrare le recensioni filtrate per stelle.
    public void MostraRecensioniFiltrate(String  filtro, String id_struttura, RecyclerView lista_recensioni) {
        if (filtro.equals("1 stella")){
            recensioni = firebaseFirestore.collection("Recensione")
                    .whereEqualTo("Id_Struttura",id_struttura)
                    .whereGreaterThanOrEqualTo("Voto","0.5").whereLessThanOrEqualTo("Voto","1.0");
            FirestoreRecyclerOptions<Modello_Recensione> options = new FirestoreRecyclerOptions.Builder<Modello_Recensione>().setQuery(recensioni,Modello_Recensione.class).build();
            //Adapter per l'elenco delle recensioni.
            adapter2 = new Adapter_recensioniStruttura(options);
            lista_recensioni.setHasFixedSize(true);
            lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
            lista_recensioni.setAdapter(adapter2);
        }
        else if(filtro.equals("2 stelle")){
            recensioni = firebaseFirestore.collection("Recensione")
                    .whereEqualTo("Id_Struttura",id_struttura)
                    .whereGreaterThanOrEqualTo("Voto","1.5").whereLessThanOrEqualTo("Voto","2.0");
            FirestoreRecyclerOptions<Modello_Recensione> options = new FirestoreRecyclerOptions.Builder<Modello_Recensione>().setQuery(recensioni,Modello_Recensione.class).build();
            //Adapter per l'elenco delle recensioni.
            adapter2 = new Adapter_recensioniStruttura(options);
            lista_recensioni.setHasFixedSize(true);
            lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
            lista_recensioni.setAdapter(adapter2);
        }
        else if (filtro.equals("3 stelle")){
            recensioni = firebaseFirestore.collection("Recensione")
                    .whereEqualTo("Id_Struttura",id_struttura)
                    .whereGreaterThanOrEqualTo("Voto","2.5").whereLessThanOrEqualTo("Voto","3.0");
            FirestoreRecyclerOptions<Modello_Recensione> options = new FirestoreRecyclerOptions.Builder<Modello_Recensione>().setQuery(recensioni,Modello_Recensione.class).build();
            //Adapter per l'elenco delle recensioni.
            adapter2 = new Adapter_recensioniStruttura(options);
            lista_recensioni.setHasFixedSize(true);
            lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
            lista_recensioni.setAdapter(adapter2);
        }
        else if(filtro.equals("4 stelle")){
            recensioni = firebaseFirestore.collection("Recensione")
                    .whereEqualTo("Id_Struttura",id_struttura)
                    .whereGreaterThanOrEqualTo("Voto","3.5").whereLessThanOrEqualTo("Voto","4.0");
            FirestoreRecyclerOptions<Modello_Recensione> options = new FirestoreRecyclerOptions.Builder<Modello_Recensione>().setQuery(recensioni,Modello_Recensione.class).build();
            //Adapter per l'elenco delle recensioni.
            adapter2 = new Adapter_recensioniStruttura(options);
            lista_recensioni.setHasFixedSize(true);
            lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
            lista_recensioni.setAdapter(adapter2);
        }
        else if (filtro.equals("5 stelle")){
            recensioni = firebaseFirestore.collection("Recensione")
                    .whereEqualTo("Id_Struttura",id_struttura)
                    .whereGreaterThanOrEqualTo("Voto","4.5").whereLessThanOrEqualTo("Voto","5.0");
            FirestoreRecyclerOptions<Modello_Recensione> options = new FirestoreRecyclerOptions.Builder<Modello_Recensione>().setQuery(recensioni,Modello_Recensione.class).build();
            //Adapter per l'elenco delle recensioni.
            adapter2 = new Adapter_recensioniStruttura(options);
            lista_recensioni.setHasFixedSize(true);
            lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
            lista_recensioni.setAdapter(adapter2);
        }
    }

    //Metodo per mostrare all'utente tutte le recensioni che ha pubblicato.
    public void MostraRecensioniPersonali(RecyclerView lista_recensioni, String id_utente) {
        recensioni = firebaseFirestore.collection("Recensione").whereEqualTo("Id_Autore",id_utente).orderBy("Voto",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Modello_Recensione> options = new FirestoreRecyclerOptions.Builder<Modello_Recensione>().setQuery(recensioni,Modello_Recensione.class).build();
        adapter3 = new Adapter_recensioniPersonali(options);     //Adapter per la gestione dell'elenco recensioni.
        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
        lista_recensioni.setAdapter(adapter3);
    }

    //Metodo per tornare al menù personale.
    public void btnMenuPersonale() {
        Intent esci3 = new Intent(Context, Menu_Personale.class);
        Context.startActivity(esci3);
        Context.finish();
    }

}
