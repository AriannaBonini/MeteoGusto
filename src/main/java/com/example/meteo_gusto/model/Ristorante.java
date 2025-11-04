package com.example.meteo_gusto.model;


import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import java.math.BigDecimal;
import java.time.LocalTime;

public class Ristorante {
    private String partitaIVA;
    private Persona proprietario;
    private String nomeRistorante;
    private String telefonoRistorante;
    private BigDecimal mediaStelle;
    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;
    private String indirizzoCompleto;
    private String citta;
    private String cap;
    private LocalTime inizioPranzo;
    private LocalTime finePranzo;
    private LocalTime inizioCena;
    private LocalTime fineCena;


    /* COSTRUTTORE CON PARAMETRI */
    public Ristorante(String partitaIVA, Persona proprietario, String nomeRistorante, String telefonoRistorante, TipoCucina cucina, FasciaPrezzoRistorante fasciaPrezzo, String indirizzoCompleto, String citta, String cap,BigDecimal mediaStelle) {
        this.partitaIVA = partitaIVA;
        this.proprietario = proprietario;
        this.nomeRistorante = nomeRistorante;
        this.telefonoRistorante = telefonoRistorante;
        this.cucina = cucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.indirizzoCompleto = indirizzoCompleto;
        this.citta = citta;
        this.cap = cap;
        this.mediaStelle=mediaStelle;
    }

    public Ristorante(String partitaIVA) {this.partitaIVA=partitaIVA;}


    /* METODI GETTER E SETTER */
    public void setPartitaIVA(String partitaIVA) {this.partitaIVA = partitaIVA;}
    public void setNomeRistorante(String nomeRistorante) {this.nomeRistorante = nomeRistorante;}
    public void setTelefonoRistorante(String telefonoRistorante) {this.telefonoRistorante = telefonoRistorante;}
    public String getPartitaIVA() {return partitaIVA;}
    public String getNomeRistorante() {return nomeRistorante;}
    public String getTelefonoRistorante() {return telefonoRistorante;}
    public Persona getProprietario() {return proprietario;}
    public void setProprietario(Persona proprietario) {this.proprietario = proprietario;}
    public BigDecimal getMediaStelle() {return mediaStelle;}
    public void setMediaStelle(BigDecimal mediaStelle) {this.mediaStelle = mediaStelle;}
    public TipoCucina getCucina() {return cucina;}
    public void setCucina(TipoCucina cucina) {this.cucina = cucina;}
    public FasciaPrezzoRistorante getFasciaPrezzo() {return fasciaPrezzo;}
    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) {this.fasciaPrezzo = fasciaPrezzo;}
    public String getIndirizzoCompleto() {return indirizzoCompleto;}
    public void setIndirizzoCompleto(String indirizzoCompleto) {this.indirizzoCompleto = indirizzoCompleto;}
    public String getCitta() {return citta;}
    public void setCitta(String citta) {this.citta = citta;}
    public String getCap() {return cap;}
    public void setCap(String cap) {this.cap = cap;}
    public LocalTime getInizioPranzo() {return inizioPranzo;}
    public void setInizioPranzo(LocalTime inizioPranzo) {this.inizioPranzo = inizioPranzo;}
    public LocalTime getFinePranzo() {return finePranzo;}
    public void setFinePranzo(LocalTime finePranzo) {this.finePranzo = finePranzo;}
    public LocalTime getInizioCena() {return inizioCena;}
    public void setInizioCena(LocalTime inzioCena) {this.inizioCena = inzioCena;}
    public LocalTime getFineCena() {return fineCena;}
    public void setFineCena(LocalTime fineCena) {this.fineCena = fineCena;}


    /* METODI DI SUPPORTO */
    public String getVia() {
        if (indirizzoCompleto == null || !indirizzoCompleto.contains(",")) return indirizzoCompleto;
        return indirizzoCompleto.substring(0, indirizzoCompleto.indexOf(",")).trim();
    }

    public String getCivico() {
        if (indirizzoCompleto == null || !indirizzoCompleto.contains(",")) return "";
        return indirizzoCompleto.substring(indirizzoCompleto.indexOf(",") + 1).trim();
    }


    /* METODI DI LOGICA DI DOMINIO */
    public void validaOrariPranzo() throws ValidazioneException {
        if (inizioPranzo != null && finePranzo != null && !finePranzo.isAfter(inizioPranzo)) {
            throw new ValidazioneException("L'orario di fine pranzo deve essere successivo a quello di inizio pranzo.");
        }
    }

    public void validaOrariCena() throws ValidazioneException {
        if (inizioCena != null && fineCena != null && !fineCena.isAfter(inizioCena)) {
            throw new ValidazioneException("L'orario di fine cena deve essere successivo a quello di inizio cena.");
        }
    }


}
