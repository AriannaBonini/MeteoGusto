package com.example.meteo_gusto.model;


import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Ristorante {
    private String partitaIVA;
    private String nomeRistorante;
    private String telefonoRistorante;
    private BigDecimal mediaStelle;
    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;
    private GiorniEOrari giorniEOrari;
    private Posizione posizione;
    private Set<TipoDieta> tipoDieta;
    private List<Ambiente> ambienteRistorante;
    private String ristoratore;

    public Ristorante() { /* COSTRUTTORE VUOTO */}


    /* COSTRUTTORE CON PARAMETRI */
    public Ristorante(String partitaIVA, String nomeRistorante, String telefonoRistorante, TipoCucina cucina, FasciaPrezzoRistorante fasciaPrezzo,Posizione posizione, GiorniEOrari giorniEOrari) {
        this.partitaIVA = partitaIVA;
        this.nomeRistorante = nomeRistorante;
        this.telefonoRistorante = telefonoRistorante;
        this.cucina = cucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.posizione=posizione;
        this.giorniEOrari = giorniEOrari;
    }

    public Ristorante(String partitaIVA) {this.partitaIVA=partitaIVA;}


    /* METODI GETTER E SETTER */
    public void setPartitaIVA(String partitaIVA) {this.partitaIVA = partitaIVA;}
    public void setNomeRistorante(String nomeRistorante) {this.nomeRistorante = nomeRistorante;}
    public void setTelefonoRistorante(String telefonoRistorante) {this.telefonoRistorante = telefonoRistorante;}
    public String getPartitaIVA() {return partitaIVA;}
    public String getNomeRistorante() {return nomeRistorante;}
    public String getTelefonoRistorante() {return telefonoRistorante;}
    public BigDecimal getMediaStelle() {return mediaStelle;}
    public void setMediaStelle(BigDecimal mediaStelle) {this.mediaStelle = mediaStelle;}
    public TipoCucina getCucina() {return cucina;}
    public void setCucina(TipoCucina cucina) {this.cucina = cucina;}
    public FasciaPrezzoRistorante getFasciaPrezzo() {return fasciaPrezzo;}
    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) {this.fasciaPrezzo = fasciaPrezzo;}
    public GiorniEOrari getOrari() {return giorniEOrari;}
    public void setOrari(GiorniEOrari giorniEOrari) {this.giorniEOrari = giorniEOrari;}
    public Posizione getPosizione() {return posizione;}
    public void setPosizione(Posizione posizione) {this.posizione = posizione;}
    public Set<TipoDieta> getTipoDieta() {return tipoDieta;}
    public void setTipoDieta(Set<TipoDieta> tipoDieta) {this.tipoDieta = tipoDieta;}
    public List<Ambiente> getAmbienteRistorante() {return ambienteRistorante;}
    public void setAmbienteRistorante(List<Ambiente> ambienteRistorante) {this.ambienteRistorante = ambienteRistorante;}
    public String getRistoratore() {return ristoratore;}
    public void setRistoratore(String ristoratore) {this.ristoratore = ristoratore;}
}
