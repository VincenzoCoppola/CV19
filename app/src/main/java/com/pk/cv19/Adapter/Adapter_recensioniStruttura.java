/*Adapter utilizzato per la gestione della lista di tutte le recensioni collegate ad una struttura*/

package com.pk.cv19.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pk.cv19.Modelli.Modello_Recensione;
import com.pk.cv19.R;

public class Adapter_recensioniStruttura extends FirestoreRecyclerAdapter<Modello_Recensione,Adapter_recensioniStruttura.NoteHolder>{

    /*-----Dichiarazioni-----*/
    String scelta;
    String Nome;
    String Cognome;
    String NomeCognome;

    /*-----Metodi-----*/

    public Adapter_recensioniStruttura(@NonNull FirestoreRecyclerOptions<Modello_Recensione> options) {
        super(options);
    }

    //Per ogni recensione di una struttura, ci sarà il titolo, testo ,valutazioe e il Nick dell'autore.
    @Override
    protected void onBindViewHolder(@NonNull Adapter_recensioniStruttura.NoteHolder holder, int position, @NonNull Modello_Recensione model) {
        Nickname(holder,model.getId_Autore());
        holder.Testo.setText(model.getTesto());
        holder.rBar.setRating(Float.parseFloat(model.getVoto()));
        holder.Titolo.setText(model.getTitolo());

    }

    //Metodo che recupera il Nick o il Nome e Cognome dell'autore in base
    // alla sua scelta in fase di registrazione.
    private void Nickname(final NoteHolder noteholder, String Id_utente) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        final DocumentReference datiUtente = database.collection("Utenti").document(Id_utente);

        datiUtente.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        scelta= String.valueOf(document.get("Scelta"));
                        if (scelta.equals("1")){        //Se l'utente ha deciso di farsi vedere col nome e cognome.
                            Nome = document.getString("Nome");
                            Cognome = document.getString("Cognome");
                            NomeCognome = Nome + " " + Cognome ;
                            noteholder.Nickname.setText(NomeCognome);

                        }
                        else if(scelta.equals("2")) {       //Altrimenti col nickname.
                            noteholder.Nickname.setText(document.getString("Nick"));
                        }
                    }
                    else {      //In questo caso, non trova l'utente.
                        NomeCognome = "Utente eliminato";
                        noteholder.Nickname.setText(NomeCognome);
                    }

                }
            }
        });
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recensioni_item_single,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView Nickname;
        TextView Testo;
        RatingBar rBar;
        TextView Titolo;

        public NoteHolder(View itemView) {
            super(itemView);
            Nickname = itemView.findViewById(R.id.list_Autore);
            Testo=itemView.findViewById(R.id.list_Recensione);
            rBar=itemView.findViewById(R.id.list_Voto);
            Titolo = itemView.findViewById(R.id.list_Titolo);
        }
    }


}

