/*Gestione adapter che si occupa dell'elenco delle strutture*/

package com.pk.cv19.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pk.cv19.Modelli.Modello_Struttura;
import com.pk.cv19.R;
import com.squareup.picasso.Picasso;



public class Adapter_Struttura extends FirestoreRecyclerAdapter<Modello_Struttura, Adapter_Struttura.StruttViewHolder> {

    /*-----Dichiarazioni-----*/
    private OnItemClickListner onListItemClick;

    /*-----Metodi-----*/

    public Adapter_Struttura(@NonNull FirestoreRecyclerOptions<Modello_Struttura> options) {
        super(options);
    }

    //Per ogni struttura, ci sarà il nome della struttura, la sua descrizione, la
    // sua valutazione media e una sua immagine rappresentativa.
    @Override
    protected void onBindViewHolder(@NonNull StruttViewHolder holder, int position, @NonNull Modello_Struttura model) {
        holder.list_name.setText(model.getNome());
        holder.list_desc.setText(model.getDescrizione());
        holder.list_voto2.setRating(model.getValutazione());
        //Utilizzo libreria Picasso per la gestione delle immagini
        Picasso.get().load(model.getImmagine()).into(holder.list_image);
    }

    @NonNull
    @Override
    public StruttViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new StruttViewHolder(view);
    }


    class StruttViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_desc;
        private ImageView list_image;
        private RatingBar list_voto2;

        public StruttViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_name);
            list_desc = itemView.findViewById(R.id.list_description);
            list_image = itemView.findViewById(R.id.list_image);
            list_voto2 = itemView.findViewById(R.id.list_Voto2);

            //Cliccando su una delle strutture nella lista , si possono
            // vedere informazioni aggiuntive e le sue recensioni.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
                        onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    //Metodi per la gestione del tocco di un elemento della lista delle strutture.
    public interface OnItemClickListner{
        void onItemClick(DocumentSnapshot docSnapshot,int position);
    }

    public void setOnItemClickListner(OnItemClickListner onListItemClick){
        this.onListItemClick = onListItemClick;
    }
}