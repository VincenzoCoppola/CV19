package com.pk.cv19.MappaESplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.pk.cv19.RegistrazioneELogin.LOGIN;
import com.pk.cv19.R;


public class SplashScreen extends AppCompatActivity {
    /*-----Dichiarazioni-----*/
    Handler handler = new Handler();

    /*-----Metodi-----*/

    //Metodo che si occupa di mostrare lo screen iniziale di presentazione.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent splash = new Intent (this, LOGIN.class);
        setContentView(R.layout.activity_splash_screen);
        handler.postDelayed(new Runnable() {
            public void run() {
              startActivity(splash);
                finish();
            }
        }, 3000);
    }

    //Metodo che disabilita il tasto back mentre l'app si inizializza.
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Caricamento in corso! ", Toast.LENGTH_SHORT).show();
    }
}