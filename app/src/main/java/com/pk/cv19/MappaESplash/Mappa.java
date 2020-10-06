/*Gestione della mappa tramite API di Google*/

package com.pk.cv19.MappaESplash;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.pk.cv19.R;

public class Mappa extends FragmentActivity implements OnMapReadyCallback {

    /*-----Dichiarazioni-----*/
    private MappaESplash_Controller Controller = new MappaESplash_Controller(this);
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    /*-----Metodi-----*/

    //Metodo che verifica se il permesso al GPS è stato accettato e
    //si comporta di conseguenza.
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {       //Controllo dei permessi
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 10, locationListener);//In caso di successo, recupera la posizione dell'utente
                AggiuntaMarkerStrutture(mMap);
                if(!locationManager.isLocationEnabled()) {
                    Controller.PermessiOK(mMap);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mappa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //Quando la mappa è pronta inizieranno i vari processi
    //per geolocalizzare l'utente e aggiungere i markers.
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {  //Appena si cambia la posizione, viene impostata su quella dell'utente.
                Controller.GpsAttivo(location, mMap);      //Richiamo metodo.
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(Mappa.this, "Geolocalizzazione in corso...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        //Controllo delle versioni SDK.
        if (Build.VERSION.SDK_INT >= 23){
            //Se SDK >= 23.
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                //Chiedo Permesso.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                //Abbiamo gia i permessi.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,100,locationListener);
                Location ultima_pos =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Controller.Version(ultima_pos,mMap);
            }
            if (!locationManager.isLocationEnabled()){
                Toast.makeText(this,"POSIZIONE IMPOSTATA SU MSA",Toast.LENGTH_LONG).show();
            }
        }
        else{
            //Se SDK < 23.
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                //Chiedo Permesso.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                //Abbiamo gia i permessi.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,100,locationListener);
                Location ultima_pos =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Controller.Version(ultima_pos,mMap);
            }
        }
    }

    //Imposta i marker delle strutture inserite nel database sulla mappa.
    private void AggiuntaMarkerStrutture(final GoogleMap mMap) {
        Controller.AggiuntaMarkerStrutture(mMap);
    }

    //Tasto back fisico.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Controller.btnMain();
    }
}


