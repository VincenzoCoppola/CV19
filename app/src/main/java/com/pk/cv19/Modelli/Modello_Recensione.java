package com.pk.cv19.Modelli;

public class Modello_Recensione {

    /*-----Dichiarazioni-----*/
    private String Testo;
    private String Id_Autore;
    private String Voto;
    private String Id_Struttura;
    private String Titolo;

    private Modello_Recensione(){
        //Vuota volutamente.
    }

    private Modello_Recensione(String Testo, String Id_Autore, String Voto, String Id_Struttura, String Titolo){
        this.Testo = Testo;
        this.Id_Autore = Id_Autore;
        this.Voto = Voto;
        this.Id_Struttura = Id_Struttura;
        this.Titolo = Titolo;
    }

    /*-----Getter & Setter-----*/

    //Metodo che recupera il titolo di una recensione.
    public String getTitolo() {
        return Titolo;
    }

    //Metodo che recupera il testo di una recensione.
    public String getTesto() {
        return Testo;
    }

    //Metodo che recupera l'id dell'autore.
    public String getId_Autore() {
        return Id_Autore;
    }

    //Metodo che recupera la valutazione di una recensione.
    public String getVoto() {
        return Voto;
    }

    //Metodo che recupera l'id della struttura recensita.
    public String getId_Struttura() {
        return Id_Struttura;
    }
}
