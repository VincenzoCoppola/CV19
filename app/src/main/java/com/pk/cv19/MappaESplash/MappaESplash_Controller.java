package com.pk.cv19.MappaESplash;

import android.content.Intent;
import android.location.Location;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pk.cv19.RecensioniEStrutture.MainActivity;
import com.pk.cv19.RecensioniEStrutture.Mostra_Struttura_Dettaglio;

import java.util.Map;

public class MappaESplash_Controller {

    /*-----Dichiarazioni-----*/
    private final Mappa Context;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("Strutture");

    /*-----Metodi-----*/

    //Metodo che setta il contesto.
    public MappaESplash_Controller(Mappa Activity) {Context = Activity;}

    //Metodo che setta la posizione standard su Monte Sant'Angelo
    //quando si hanno i permessi ma non la geolocalizzazione.
    public void PermessiOK(GoogleMap mMap) {
        LatLng latLng2;
        latLng2 = new LatLng(40.838599, 14.187656);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng2).title("MSA")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 10));
    }

    //Metodo che setta la posizione dell'utente.
    public void GpsAttivo(Location location, GoogleMap mMap) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();       //Impedisce di far comparire lo stesso marker nella medesima posizione.
        mMap.addMarker(new MarkerOptions().position(latLng).title("Tu")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));      //Sposta la visuale sul marker utente
        AggiuntaMarkerStrutture(mMap);
    }

    //Metodo che permette ai dispositivi con SDK < 23 di far
    //eseguire correttamente API di Google.
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void Version(Location ultima_pos, GoogleMap mMap) {
        LatLng latLng;
        if (ultima_pos != null) {
            latLng = new LatLng(ultima_pos.getLatitude(),ultima_pos.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Tu")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
            AggiuntaMarkerStrutture(mMap);

        }else{      //Se non riesce a recuperare la posizione attuale dell'utente, imposta il marker standard su MSA Federico II.
            latLng = new LatLng(40.838599,14.187656);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("MSA")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
            AggiuntaMarkerStrutture(mMap);
        }
    }

    //Metodo che aggiunge i marker di ogni struttura sulla mappa.
    public void AggiuntaMarkerStrutture(final GoogleMap mMap) {
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {     //Recupero delle strutture dal database.
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot collectionReference : task.getResult()){
                        Map<String, Object> dati = collectionReference.getData();
                        Double lat = (Double) dati.get("Latitudine");       //Serve per la localizzazione.
                        Double lng = (Double) dati.get("Longitudine");      //..
                        String Nome = (String) dati.get("Nome");
                        String tipo = (String) dati.get("Tipo");
                        String Città = (String) dati.get("Città");
                        String TIPO;
                        if (tipo.equals("Ris")){
                            TIPO = "Ristorante";
                        }
                        else if (tipo.equals("Hot")){
                            TIPO = "Hotel";
                        }
                        else{
                            TIPO = "Attrazioni";
                        }
                        LatLng coordinate_struttura = new LatLng(lat,lng);
                        mMap.addMarker(new MarkerOptions().position(coordinate_struttura).title(Nome)
                                .snippet(TIPO+" "+Città)).setIcon(Colore((String)dati.get("Tipo")));
                    }
                }
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                final String Nome = marker.getTitle();
                collectionReference.whereEqualTo("Nome",Nome).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                            StrutturaSelezionata(document.getId());
                        }
                    }
                });
            }
        });
    }

    //Metodo che recupera la struttura interessata dal click dell'utente.
    public void StrutturaSelezionata(String id) {
        Intent mostra_struttura = new Intent(Context, Mostra_Struttura_Dettaglio.class);
        mostra_struttura.putExtra("id",id);
        Context.startActivity(mostra_struttura);
    }

    //Metodo che setta il colore del marker di una struttura
    //in base al tipo di quest'ultima.
    public BitmapDescriptor Colore(String tipo) {
        if(tipo.equals("Ris")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }else if(tipo.equals("Hot")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        }else{
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        }
    }

    //La pressione del pulsante fisico back porta l'utente
    //nella schermata principale.
    public void btnMain() {
        Intent main = new Intent(Context, MainActivity.class);
        Context.startActivity(main);
        Context.finish();
    }


}

