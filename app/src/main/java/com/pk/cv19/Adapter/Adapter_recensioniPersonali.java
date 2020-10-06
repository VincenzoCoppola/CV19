/*Adapter utilizzato per la gestione della lista delle recensioni personali*/

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

public class Adapter_recensioniPersonali extends FirestoreRecyclerAdapter<Modello_Recensione,Adapter_recensioniPersonali.NoteHolder> {


    public Adapter_recensioniPersonali(@NonNull FirestoreRecyclerOptions<Modello_Recensione> options) {
        super(options);
    }

    //Per ogni recensione personale, ci sarà il titolo, testo ,valutazione e il nome
    //della struttura recensita.
    @Override
    protected void onBindViewHolder(@NonNull Adapter_recensioniPersonali.NoteHolder holder, int position, @NonNull Modello_Recensione model) {
        NomeStruttura(holder,model.getId_Struttura());      //Richiamo metodo
        holder.Valutazione.setRating(Float.parseFloat(model.getVoto()));
        holder.Testo.setText(model.getTesto());
        holder.Titolo.setText(model.getTitolo());
    }

    //Metodo che recupera il nome della struttura recensita.
    private void NomeStruttura(final NoteHolder holder, String id_struttura) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        final DocumentReference datiUtente = database.collection("Strutture").document(id_struttura);
        datiUtente.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        holder.Nome_Struttura.setText(document.getString("Nome"));
                    }

                }
            }
        });
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recensioni_personali_item_single,
                parent, false);
        return new NoteHolder(v);
    }



    class NoteHolder extends RecyclerView.ViewHolder{
        TextView Testo;
        RatingBar Valutazione;
        TextView Titolo;
        TextView Nome_Struttura;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            Testo = itemView.findViewById(R.id.tvTestoRecensione);
            Valutazione = itemView.findViewById(R.id.tvVoto);
            Nome_Struttura = itemView.findViewById(R.id.tvNomeStruttura);
            Titolo = itemView.findViewById(R.id.tvTitoloRecensione);
        }
    }
}
