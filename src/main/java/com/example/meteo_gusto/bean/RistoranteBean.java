package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import java.math.BigDecimal;
import java.time.LocalTime;

public class RistoranteBean {
    private String partitaIVA;
    private PersonaBean proprietario;
    private String nomeRistorante;
    private String telefonoRistorante;
    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;
    private BigDecimal mediaStelle;
    private String indirizzoCompleto;
    private String citta;
    private String cap;
    private LocalTime inizioPranzo;
    private LocalTime finePranzo;
    private LocalTime inizioCena;
    private LocalTime fineCena;

    public RistoranteBean() { /* COSTRUTTORE VUOTO */ }

    public RistoranteBean(String partitaIVA,PersonaBean proprietario, String nomeRistorante, String telefonoRistorante, TipoCucina cucina, FasciaPrezzoRistorante fasciaPrezzo, String indirizzoCompleto, String cap, String citta) throws ValidazioneException {
        validazionePartitaIVA(partitaIVA);
        validazioneNomeRistorante(nomeRistorante);
        validazioneTelefonoRistorante(telefonoRistorante);
        validazioneCucina(cucina);
        validazioneFasciaPrezzo(fasciaPrezzo);
        validazioneIndirizzoCompleto(indirizzoCompleto);
        validazioneCap(cap);
        validazioneCitta(citta);

        this.partitaIVA = partitaIVA.trim();
        this.proprietario=proprietario;
        this.nomeRistorante = nomeRistorante.trim();
        this.telefonoRistorante = telefonoRistorante.trim();
        this.cucina = cucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.indirizzoCompleto = indirizzoCompleto;
        this.cap=cap;
        this.citta=citta;
    }


    /* METODI DI VALIDAZIONE SINTATTICA */
    private void validazionePartitaIVA(String partitaIVA) throws ValidazioneException{
        if (partitaIVA == null || partitaIVA.trim().isEmpty()) {
            throw new ValidazioneException("La partita IVA non può essere vuota.");
        }
        if (!partitaIVA.matches("^0\\d{10}$")) {
            throw new ValidazioneException("La partita IVA deve contenere 11 cifre e iniziare con 0.");
        }
    }

    private void validazioneNomeRistorante(String nomeRistorante) throws ValidazioneException{
        if (nomeRistorante == null || nomeRistorante.trim().isEmpty()) {
            throw new ValidazioneException("Il nome del ristorante non può essere vuoto.");
        }
        if (!nomeRistorante.matches("^[a-zA-Zàèéìòùçñ'\\s]+$")) {
            throw new ValidazioneException("Il nome può contenere solo lettere e spazi.");
        }
    }

    private void validazioneTelefonoRistorante(String telefonoRistorante) throws ValidazioneException{
        if (telefonoRistorante == null || telefonoRistorante.trim().isEmpty()) {
            throw new ValidazioneException("Il numero di telefono non può essere vuoto.");
        }
        if (!telefonoRistorante.matches("^\\d{10,15}$")) {
            throw new ValidazioneException("Il telefono deve contenere solo numeri (10-15 cifre).");
        }
    }

    private void validazioneCucina(TipoCucina cucina) throws ValidazioneException{
        if (cucina == null) {
            throw new ValidazioneException("La cucina del ristorante è obbligatoria.");
        }
    }

    private void validazioneFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) throws ValidazioneException {
        if (fasciaPrezzo == null) {
            throw new ValidazioneException("La fascia di prezzo è obbligatoria.");
        }
    }
    private void validazioneIndirizzoCompleto(String indirizzoCompleto) throws ValidazioneException{
        if (indirizzoCompleto == null || indirizzoCompleto.trim().isEmpty()) {
            throw new ValidazioneException("Il campo indirizzo è obbligatorio.");
        }
        if (!indirizzoCompleto.matches(".+,\\s*\\d+")) {
            throw new ValidazioneException("L'indirizzo deve contenere una virgola seguita dal civico (es. Via Roma, 12).");
        }
    }

    private void validazioneCitta(String citta) throws ValidazioneException{
        if (citta == null || citta.trim().isEmpty()) {
            throw new ValidazioneException("La città non può essere vuota.");
        }
        if (!citta.matches("^[a-zA-Zàèéìòùçñ ]+$")) {
            throw new ValidazioneException("La città può contenere solo lettere e spazi.");
        }
    }

    private void validazioneCap(String cap) throws ValidazioneException{
        if (cap == null || cap.trim().isEmpty()) {
            throw new ValidazioneException("Il CAP non può essere vuoto.");
        }
        if (!cap.matches("^\\d{5}$")) {
            throw new ValidazioneException("Il CAP deve essere un numero di 5 cifre.");
        }
    }


    /* METORI GETTER E SETTER */

    public void setCap(String cap) {this.cap = cap.trim();}
    public String getPartitaIVA() { return partitaIVA; }
    public String getNomeRistorante() { return nomeRistorante; }
    public String getTelefonoRistorante() { return telefonoRistorante; }
    public PersonaBean getProprietario() { return proprietario; }
    public TipoCucina getCucina() { return cucina; }
    public FasciaPrezzoRistorante getFasciaPrezzo() { return fasciaPrezzo; }
    public BigDecimal getMediaStelle() { return mediaStelle; }
    public String getIndirizzoCompleto() { return indirizzoCompleto; }
    public String getCitta() { return citta; }
    public String getCap() { return cap; }
    public LocalTime getInizioPranzo() { return inizioPranzo; }
    public LocalTime getFinePranzo() { return finePranzo; }
    public LocalTime getInizioCena() { return inizioCena; }
    public LocalTime getFineCena() { return fineCena; }
    public void setPartitaIVA(String partitaIVA) {this.partitaIVA = partitaIVA;}
    public void setNomeRistorante(String nomeRistorante) {this.nomeRistorante = nomeRistorante;}
    public void setTelefonoRistorante(String telefonoRistorante)  {this.telefonoRistorante = telefonoRistorante;}
    public void setProprietario(PersonaBean proprietario){this.proprietario = proprietario;}
    public void setCucina(TipoCucina cucina)  {this.cucina = cucina;}
    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) {this.fasciaPrezzo = fasciaPrezzo;}
    public void setMediaStelle(BigDecimal mediaStelle) {this.mediaStelle = mediaStelle;}
    public void setIndirizzoCompleto(String indirizzoCompleto) {this.indirizzoCompleto = indirizzoCompleto;}
    public void setCitta(String citta) {this.citta = citta;}
    public void setInizioPranzo(LocalTime inizioPranzo) {this.inizioPranzo = inizioPranzo;}
    public void setFinePranzo(LocalTime finePranzo) {this.finePranzo = finePranzo;}
    public void setInizioCena(LocalTime inizioCena) {this.inizioCena = inizioCena;}
    public void setFineCena(LocalTime fineCena) {this.fineCena = fineCena;}
}