package com.pk.cv19.Menù;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pk.cv19.R;

public class Menu_Personale extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private Menu_Controller Controller = new Menu_Controller(this);
    private FirebaseAuth mAuth;
    private FirebaseUser utente =FirebaseAuth.getInstance().getCurrentUser();
    String Email;
    TextView tvEmail;
    TextView tvUtente;
    TextView tvNick;

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__personale);
        mAuth = FirebaseAuth.getInstance();
        Email=utente.getEmail();
        tvEmail=findViewById(R.id.textViewEmail);
        tvUtente=findViewById(R.id.textViewUtente);
        tvNick=findViewById(R.id.textViewNickName);
        tvEmail.setText(Email);

        trovaNomeeCognomeeNick(utente.getUid());


    }

    //Metodo che si occupa di recuperare Nome, cognome e nickname dal database.
    private void trovaNomeeCognomeeNick(final String uid) {
        Controller.TrovaDati(uid,tvNick,tvUtente);
    }

    //Tasto back su schermo.
    public void TornaMain(View view) {
        Controller.btnMain();
    }

    //Tasto di logout.
    public void FaiLogout(View view) {
        mAuth.signOut();
        Controller.faiLogout();
    }

    //Tasto per mostrare tutte le recensioni dell'utente collegato.
    public void Mostra_recensioni_personali(View view) {
        Controller.Rec_Pers();
    }

    //Tasto back (fisico).
    @Override
    public void onBackPressed() {
        Controller.btnMain();
    }
}