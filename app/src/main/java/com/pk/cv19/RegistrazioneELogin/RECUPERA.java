package com.pk.cv19.RegistrazioneELogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pk.cv19.R;

public class RECUPERA extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private RegistrazioneELogin_Controller Controller = new RegistrazioneELogin_Controller(this);

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_e_c_u_p_e_r);
    }

    //Metodo che si occupa del recupero password.
    public void RecuperaPass(View view) {
        EditText RecEmail = (EditText) findViewById(R.id.editTextEmail);
        String email = RecEmail.getText().toString();

        //Vengono effettuati vari controlli.
        if (email.isEmpty()) {
            Toast.makeText(this, "Campo vuoto!",
                    Toast.LENGTH_LONG).show();
        } else if (!email.contains("@") || email.length() < 10) {
            Toast.makeText(this, "Email non valida!",
                    Toast.LENGTH_LONG).show();
        } else {
            //Se i controlli sono superati, allora si effettua il recupero all'interno del database.
            Controller.recoveryFBUser(email);
        }
    }

    //Quando si preme il bottone indietro (presente sullo schermo) viene richiamato questo metodo che si occupa di ritornare alla schermata di LOGIN.
    public void TornaLogin(View view) {
        Controller.updateUIRec();
    }

    //Se invece si preme il tasto indietro (fisico), effettuerà la stessa operazione di prima, ovvero ritornare alla schermata di Login.
    @Override
    public void onBackPressed() {
        Controller.updateUIRec();
        finish();
    }
}