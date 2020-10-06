package com.pk.cv19.Menù;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pk.cv19.RecensioniEStrutture.MainActivity;
import com.pk.cv19.RecensioniEStrutture.Recensioni_Personali;
import com.pk.cv19.RegistrazioneELogin.LOGIN;
import com.pk.cv19.RegistrazioneELogin.Registrazione;

public class Menu_Controller {

    /*-----Dichiarazioni-----*/
    private final AppCompatActivity Context;
    private FirebaseAuth mAuth;
    private FirebaseUser utente =FirebaseAuth.getInstance().getCurrentUser();
    String Nome;
    String Cognome;
    String NomeeCognome;
    String Nick;

    /*-----Metodi-----*/

    //Metodo che setta il contesto.
    public Menu_Controller(AppCompatActivity Activity) {
        Context = Activity;
    }

    //Metodo per passare alla schermata di registrazione.
    public void PassaAReg() {
        Intent reg = new Intent(Context, Registrazione.class);
        Context.startActivity(reg);
    }

    //Metodo per passare alla schermata principale.
    public void PassaAlMain() {
        Intent back = new Intent((android.content.Context) Context, MainActivity.class);
        Context.startActivity(back);
        Context.finish();
    }

    //Metodo che recupera i dati dell'utente loggato.
    public void TrovaDati(final String uid, final TextView tvNick, final TextView tvUtente) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference dati = db.collection("Utenti").document(uid);
        dati.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){     //Se l'utente esiste allora recupera i suoi dati personali.
                        Nome = document.getString("Nome");
                        Cognome = document.getString("Cognome");
                        NomeeCognome = Nome + " " + Cognome ;
                        Nick = document.getString("Nick");
                        tvNick.setText(Nick);
                        tvUtente.setText(NomeeCognome);
                    }
                    else{
                        //In caso di fallimento, messaggio d'errore.
                        Toast.makeText(Context,"Nessun documento trovato con id:  "+uid,Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //Metodo per passare alla schermata principale.
    public void btnMain() {
        Intent esci3 = new Intent(Context, MainActivity.class);
        Context.startActivity(esci3);
        Context.finish();
    }

    //Metodo che permette all'utente guesto o loggato di fare il logout.
    public void faiLogout() {
        Intent yoo = new Intent(Context, LOGIN.class);
        Context.startActivity(yoo);
        Context.finish();
        Toast.makeText(Context, "LOGOUT Effettuato", Toast.LENGTH_SHORT).show();
    }

    //Metodo per passare alla schermata delle recensioni personali dell'utente.
    public void Rec_Pers() {
        Intent mostra = new Intent(Context, Recensioni_Personali.class);
        mostra.putExtra("Id_Utente",utente.getUid());
        Context.startActivity(mostra);
        Context.finish();
    }
}
