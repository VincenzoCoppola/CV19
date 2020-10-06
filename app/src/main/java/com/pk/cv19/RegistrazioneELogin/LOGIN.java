/*Consiglia viaggi 2019
* Created by Umberto Dello Iacolo N86002154
*            Vincenzo Coppola N86002341
*
* Informazioni: l'app è stata creata in base all'uso di Google Firebase; pertanto molti metodi utilizzati sono quelli proposti da Firebase*/


package com.pk.cv19.RegistrazioneELogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pk.cv19.R;

public class LOGIN extends AppCompatActivity {

    //Dichiarazioni variabili globali che servono per l'autenticazione.
    private ProgressBar spinner;
    private RegistrazioneELogin_Controller Controller = new RegistrazioneELogin_Controller(this);
    EditText mEmail;
    EditText mPassword;
    String Email;
    CheckBox showPassword;

    //Inizializzazione delle variabili collegate a firebase.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  //Mostra il layout del Login (Vedere Res/Layout/activity_login).
        boolean connected=isOnline();
        if (connected==true) {
            Controller.updateUI();
            initData();  //Richiamo del metodo.
        }
        else {
            Toast.makeText(this, "SEI OFFLINE, CONTROLLA LA TUA CONNESSIONE!", Toast.LENGTH_SHORT).show();
        }
    }


    //Metodo che inizializza tutti i dati necessari all'avvio.
    private void initData() {
        spinner=(ProgressBar)findViewById(R.id.progress_loader);
        spinner.setVisibility(View.GONE);
        mEmail = (EditText) findViewById(R.id.EtEmail2);
        mPassword = (EditText) findViewById(R.id.EtPass2);
        showPassword = findViewById(R.id.showPass);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean show) {
                if (show) {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        boolean connected=isOnline();
        if (connected==true) { // Verifica se c'è connessione ad internet.
            Controller.updateUI();
        }
        else {
            Toast.makeText(this,"SEI OFFLINE, CONTROLLA LA TUA CONNESSIONE!",Toast.LENGTH_SHORT).show();
        }
    }
    //Dopo l'autenticazione dell'utente, verrà attivata la schermata della Home (controllando i dovuti campi).
    public void PassaAHome(View view) {
        Email = mEmail.getText().toString();
        String Password = mPassword.getText().toString();
        boolean isOnline = isOnline();
        if(isOnline == true) { // Verifica se c'è connessione ad internet.
            if (Email.isEmpty() || Password.isEmpty()) {
                Toast.makeText(this, "Uno o più campi sono vuoti!",
                        Toast.LENGTH_LONG).show();
            } else if (Email.length() < 10 || !Email.contains("@")) {
                Toast.makeText(this, "Email non valida!",
                        Toast.LENGTH_LONG).show();
            } else if (Password.length() < 3) {
                Toast.makeText(this, "Password non valida!",
                        Toast.LENGTH_LONG).show();
            } else {
                //Se tutti i campi inseriti sono corretti allora verrà richiamato il metodo loginUser.
                spinner.setVisibility(View.VISIBLE);
                loginUser(Email, Password);
            }
        }
        else{
            Toast.makeText(this, "SEI OFFLINE! CONTROLLA LA TUA CONNESSIONE",
                    Toast.LENGTH_LONG).show();
        }
    }

    //Metodo che controlla se email e password sono corretti.
    private void loginUser(String email, String password) {
        boolean connected=isOnline();
        if (connected==true) { // Verifica se c'è connessione ad internet.
            Controller.LoginUser(email,password,spinner);
        }
        else {
            spinner.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"SEI OFFLINE! CONTROLLA LA TUA CONNESSIONE",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo che controlla se il dispositivo è connesso a internet.
    public boolean isOnline() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    //Se nel caso, l'utente non vuole effettuare la registrazione, potrà entrare come ospite non potendo però effettuare recensioni.
    public void PassaAHomeGuest(View view) {
        boolean isOnline = isOnline();
        if (isOnline == true) { // Verifica se c'è connessione ad internet.
            spinner.setVisibility(View.VISIBLE);
            Controller.Home();
        }
        else{
            Toast.makeText(this,"SEI OFFLINE! CONTROLLA LA TUA CONNESSIONE",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo che gestisce la registrazione di un utente.
    public void PassaARegis(View view) {
        boolean isOnline = isOnline();
        if (isOnline == true) { // Verifica se c'è connessione ad internet.
            spinner.setVisibility(View.VISIBLE);
            Controller.Reg();
        }
        else{
            Toast.makeText(this,"SEI OFFLINE! CONTROLLA LA TUA CONNESSIONE",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo che gestisce il recupero della password.
    public void PassaARecupero(View view) {
        boolean isOnline = isOnline();
        if (isOnline == true ) { // Verifica se c'è connessione ad internet.
            spinner.setVisibility(View.VISIBLE);
            Controller.Rec();
        }
        else {
            Toast.makeText(this,"SEI OFFLINE! CONTROLLA LA TUA CONNESSIONE",Toast.LENGTH_SHORT).show();
        }
    }

    //Quando si ritorna alla schermata di Login, questo metodo si occupa di nascondere l'animazione del caricamento.
    protected void onResume() {
        super.onResume();
        spinner.setVisibility(View.INVISIBLE);
    }

    //Quando si preme il tasto back (tasto fisico), esce una finestra di PopUp chiedendo all'utente conferma di voler uscire.
    @Override
    public void onBackPressed() {
        Controller.BackPressed();
    }
}