/*          Controller LOGIN
*            REGISTRAZIONE
*            RECUPERA PASSWORD*/

package com.pk.cv19.RegistrazioneELogin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pk.cv19.RecensioniEStrutture.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class RegistrazioneELogin_Controller {

    /*-----Dichiarazioni-----*/
    private final Object Context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private boolean esiste = false;

    /*-----Metodi-----*/

    //Metodo per settare il contesto.
    public RegistrazioneELogin_Controller(AppCompatActivity Activity){
        Context = Activity;
    }

    //Metodo che gestisce l'aggiornamento dell'interfaccia.
    public void updateUI() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent yo = new Intent((android.content.Context) Context, MainActivity.class);  //Se l'utente esiste nel databse allora richiama l'activity main
            ((android.content.Context) Context).startActivity(yo);
            ((Activity)Context).finish();
        }
    }

    //Metodo per effettuare il login tramite email e password.
    public void LoginUser(String email, String password, final ProgressBar spinner) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) Context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText((android.content.Context) Context, "Authentication success.",Toast.LENGTH_SHORT).show();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            spinner.setVisibility(View.INVISIBLE);
                            Toast.makeText((android.content.Context) Context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Metodo per passare alla schermata principale.
    public void Home() {
        Intent intent3 = new Intent((android.content.Context) Context, MainActivity.class);
        ((android.content.Context) Context).startActivity(intent3);
        ((Activity)Context).finish();
    }

    //Metodo che controlla se i dati inseriti dall'utente non siano uguali a quelli di un altro utente.
    public void isNewUser(String Email, String Nick){
        final String email = Email.toLowerCase();
        final String nick = Nick.toLowerCase();
        CollectionReference documentReference = db.collection("Utenti");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    esiste = false;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String EMAIL = document.getString("Email").toLowerCase();
                        String NICK = document.getString("Nick").toLowerCase();
                        if (email.equals(EMAIL) || nick.equals(NICK)){
                            esiste = true;
                            break;
                        }
                    }
                    if (getExists()){
                        Toast.makeText((android.content.Context) Context,"Nick e/o Email già presenti nel database",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText((android.content.Context) Context,"Authentication failed.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //Metodo che ritorna un booleano che indica se esiste gia un utente che ha i dati uguali.
    public boolean getExists(){return esiste;}

    //Metodo per passare alla schermata di registrazione.
    public void Reg() {
        Intent intent6 = new Intent((android.content.Context) Context, Registrazione.class);
        ((android.content.Context) Context).startActivity(intent6);
        ((Activity)Context).finish();
    }

    //Metodo per passare alla schermata di recupero password.
    public void Rec() {
        Intent intent8 = new Intent((android.content.Context) Context, RECUPERA.class);
        ((android.content.Context) Context).startActivity(intent8);
        ((Activity)Context).finish();
    }

    //Alla pressione del tasto fisico back nella schermata di login , l'app chiede all'utente
    //vuole uscire dall'app e si comporta di conseguenza.
    public void BackPressed() {
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

    //Metodo che registra un nuovo utente nel database.
    public void createFBUser(final String email, String password, final String nome, final String cognome, final String nick, final String Scelta) {
        mAuth.createUserWithEmailAndPassword(email, password)       //Metodo firebase
                .addOnCompleteListener((Activity) Context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText((android.content.Context) Context, "Registration success.",Toast.LENGTH_SHORT).show();
                            AggiornaUtenti(nome,cognome,nick,Scelta,email);       //Metodo che inserisce i campi riempiti nel databse
                            updateUIReg(email);

                        }
                        else{
                            isNewUser(email,nick);
                        }
                    }
                });
    }

    //Metodo che aggiunge il nuovo utente registrato al database.
    public void AggiornaUtenti(String Nome, String Cognome , String Nick , String Scelta, String email){
        FirebaseUser Id_utente = mAuth.getCurrentUser();
        Map<String , Object> nuovo_utente = new HashMap<>();
        nuovo_utente.put("Nome",Nome);
        nuovo_utente.put("Cognome",Cognome);
        nuovo_utente.put("Nick",Nick);
        nuovo_utente.put("Id_Utente",Id_utente.getUid());
        nuovo_utente.put("Scelta",Scelta);
        nuovo_utente.put("Email",email);
        db.collection("Utenti").document(Id_utente.getUid()).set(nuovo_utente);
    }

    //Metodo per passare alla schermata principale.
    public void updateUIReg(String email) {
        Intent yo = new Intent ((android.content.Context) Context, MainActivity.class);
        yo.putExtra("Email",email);
        ((android.content.Context) Context).startActivity(yo);
        ((Activity)Context).finish();
    }

    //Metodo per tornare alla schermata di login.
    public void BackLogin() {
        Intent intent1 = new Intent((android.content.Context) Context, LOGIN.class);
        ((android.content.Context) Context).startActivity(intent1);
        ((Activity)Context).finish();
    }


    //Metodo per recuperare la password.
    public void recoveryFBUser(String email) {
        mAuth.sendPasswordResetEmail(email)     //Metodi di firebase oer il recupero password
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText((android.content.Context) Context, "Link inviato all'indirizzo email specificato", Toast.LENGTH_SHORT).show();
                            updateUIRec();     //Appena invia la password all'inidirizzo email, si viene indirizzati sulla schermata di LOGIN
                        } else {
                            Toast.makeText((android.content.Context) Context, "L'email non è presente nei nostri archivi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Metodo per tornare alla schermata di login.
    public void updateUIRec() {
        Intent yo = new Intent((android.content.Context) Context, LOGIN.class);
        ((android.content.Context) Context).startActivity(yo);      //Passa all'activity di login
        ((Activity)Context).finish();  //Si occupa di terminare l'activity
    }
}




