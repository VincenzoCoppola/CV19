package com.pk.cv19.Modelli;

public class Modello_Struttura {

    /*-----Dichiarazioni-----*/
    private String Nome;
    private String Descrizione;
    private String Immagine;
    private Float Valutazione;

    private Modello_Struttura(){
        //Vuota volutamente.
    }

    private Modello_Struttura(String Nome, String Descrizione ,String Immagine ,Float Valutazione){
        this.Nome = Nome;
        this.Descrizione = Descrizione;
        this.Immagine = Immagine;
        this.Valutazione = Valutazione;
    }

    /*-----Getter & Setter-----*/

    //Metodo che recupera il nome di una struttura.
    public String getNome() {
        return Nome;
    }

    //Metodo che setta il nome di una struttura.
    public void setNome(String nome) { Nome = nome; }

    //Metodo che recupera la descrizione di una struttura.
    public String getDescrizione() {
        return Descrizione;
    }

    //Metodo che setta la descrizione di una struttura.
    public void setDescrizione(String descrizione) { Descrizione = descrizione; }

    //Metodo che recupera l'immagine di una struttura.
    public String getImmagine() {
        return Immagine;
    }

    //Metodo che setta l'immagine di una struttura.
    public void setImmagine(String immagine) { Immagine = immagine; }

    //Metodo che recupera la valutazione di una struttura.
    public Float getValutazione() {
        return Valutazione;
    }

    //Metodo che setta la valutazione di una struttura.
    public void setValutazione(Float valutazione) {
        Valutazione = valutazione;
    }
}
