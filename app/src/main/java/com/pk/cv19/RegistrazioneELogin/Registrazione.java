package com.pk.cv19.RegistrazioneELogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pk.cv19.R;

public class Registrazione extends AppCompatActivity {

    private RegistrazioneELogin_Controller Controller = new RegistrazioneELogin_Controller(this);

    EditText mNome;
    EditText mCognome;
    EditText mEmail;
    EditText mPassword;
    EditText mNick;
    EditText mConfPass;
    String  Scelta = "0";
    String email;
    String ConfPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //Tasto back (su schermo).
    public void TornaalLogin(View view) {
        Controller.BackLogin();
    }

    //Metodo che ricava i dati dagli opportuni editText ed effettua vari controlli.
    public void Registrati(View view) {
        mNome=(EditText)findViewById(R.id.editTextTextPersonName3);
        mCognome=(EditText)findViewById(R.id.editTextTextPersonName4);
        mEmail=(EditText)findViewById(R.id.EtEmail);
        mPassword=(EditText)findViewById(R.id.EtPass);
        mNick=(EditText)findViewById(R.id.editTextTextPersonName7);
        mConfPass = (EditText)findViewById(R.id.EtConfPass);

        String nome= mNome.getText().toString();
        String cognome= mCognome.getText().toString();
        email= mEmail.getText().toString();
        String password= mPassword.getText().toString();
        ConfPass = mConfPass.getText().toString();
        String nick= mNick.getText().toString();

        //Controllo sui vari campi.
        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || nick.isEmpty() || ConfPass.isEmpty()){
            Toast.makeText(this, "Uno o più campi sono vuoti!",
                    Toast.LENGTH_LONG).show();
        }
        else if(nome.length() < 3){
            Toast.makeText(this, "Nome non valido!",
                    Toast.LENGTH_LONG).show();
        }
        else if(cognome.length() < 3){
            Toast.makeText(this, "Cognome non valido!",
                    Toast.LENGTH_LONG).show();
        }
        else if(email.length() < 10 || ! email.contains("@")){
            Toast.makeText(this, "Email non valida!",
                    Toast.LENGTH_LONG).show();
        }
        else if(password.length() < 6){
            Toast.makeText(this, "Password non valida! Min 6 car.",
                    Toast.LENGTH_LONG).show();
        }
        else if(nick.length() < 5){
            Toast.makeText(this, "Nick non valido! Min 5 car.",
                    Toast.LENGTH_LONG).show();
        }
        else if(Scelta == "0"){
            Toast.makeText(this,"Deve scegliere come vuole essere visualizzato",Toast.LENGTH_LONG).show();
        }
        else if (! password.equals(ConfPass)){
            Toast.makeText(this,"Attenzione! Password diverse ",Toast.LENGTH_LONG).show();
        }
        else{
                Controller.createFBUser(email, password, nome, cognome, nick, Scelta);
        }
    }


    //Tasto back (fisico).
    @Override
    public void onBackPressed() {
        Intent back = new Intent(this,LOGIN.class);
        startActivity(back);
        finish();
    }


    //Tramite un radioGroupButton, è possibile scegliere se essere visualizzati con NickName oppure Nome e Cognome.
    public void SceltaNick(View view) {
        Toast.makeText(this,"Verrai visualizzato con il Nick",Toast.LENGTH_LONG).show();
        Scelta = "2";
    }
    public void SceltaNomeeCognome(View view) {
        Toast.makeText(this,"Verrai visualizzato con il Nome e Cognome",Toast.LENGTH_LONG).show();
        Scelta = "1";
    }
}