package com.example.meteo_gusto.model;


import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Ristorante {
    private String partitaIVA;
    private String nome;
    private String telefono;
    private BigDecimal mediaStelle;
    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;
    private GiorniEOrari orariApertura;
    private Posizione posizione;
    private Set<TipoDieta> diete;
    private List<Ambiente> ambienti;
    private String ristoratore;

    public Ristorante() { /* COSTRUTTORE VUOTO */}


    /* COSTRUTTORE CON PARAMETRI */

    /* Costruttore per creare un oggetto Ristorante con i dati della registrazione */
    public Ristorante(String partitaIVA, String nome, String telefono, TipoCucina cucina, FasciaPrezzoRistorante fasciaPrezzo, Posizione posizione, GiorniEOrari orariApertura) {
        this.partitaIVA = partitaIVA;
        this.nome = nome;
        this.telefono = telefono;
        this.cucina = cucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.posizione=posizione;
        this.orariApertura = orariApertura;
        this.mediaStelle = BigDecimal.ZERO;

    }

    public Ristorante(String partitaIVA) {this.partitaIVA=partitaIVA;}


    /* METODI GETTER E SETTER */
    public void setPartitaIVA(String partitaIVA) {this.partitaIVA = partitaIVA;}
    public void setNome(String nome) {this.nome = nome;}
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public String getPartitaIVA() {return partitaIVA;}
    public String getNome() {return nome;}
    public String getTelefono() {return telefono;}
    public BigDecimal getMediaStelle() {return mediaStelle;}
    public void setMediaStelle(BigDecimal mediaStelle) {this.mediaStelle = mediaStelle;}
    public TipoCucina getCucina() {return cucina;}
    public void setCucina(TipoCucina cucina) {this.cucina = cucina;}
    public FasciaPrezzoRistorante fasciaPrezzoRistorante() {return fasciaPrezzo;}
    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) {this.fasciaPrezzo = fasciaPrezzo;}
    public GiorniEOrari orariApertura() {return orariApertura;}
    public void setOrariApertura(GiorniEOrari orariApertura) {this.orariApertura = orariApertura;}
    public Posizione posizioneRistorante() {return posizione;}
    public void setPosizione(Posizione posizione) {this.posizione = posizione;}
    public Set<TipoDieta> getDiete() {return diete;}
    public void setDiete(Set<TipoDieta> diete) {this.diete = diete;}
    public List<Ambiente> ambientiRistorante() {return ambienti;}
    public void setAmbienti(List<Ambiente> ambienti) {this.ambienti = ambienti;}
    public String getRistoratore() {return ristoratore;}
    public void setRistoratore(String ristoratore) {this.ristoratore = ristoratore;}
}
