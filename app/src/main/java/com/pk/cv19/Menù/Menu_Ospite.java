package com.pk.cv19.Menù;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.pk.cv19.R;

public class Menu_Ospite extends AppCompatActivity {

    /*-----Dichiarazioni-----*/
    private Menu_Controller Controller = new Menu_Controller(this);

    /*-----Metodi-----*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__ospite);
    }

    //Tasto collegato alla registrazione di un utente.
    public void PassaARegistrazione(View view) {
        Controller.PassaAReg();
    }

    //Tasto esci.
    public void PassaALogin(View view) {
        Controller.faiLogout();
    }

    //Tasti back.
    public void onBackPressed() {
        Controller.PassaAlMain();
    }
    public void TornaMain(View view) {
        Controller.PassaAlMain();
    }
    /*----------------------------------*/
}