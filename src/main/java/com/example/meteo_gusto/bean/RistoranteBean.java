package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class RistoranteBean {

    private String partitaIVA;
    private String nome;
    private String telefono;
    private PosizioneBean posizioneRistorante;
    private OffertaCulinariaBean offertaCulinaria;
    private OrariBean orari;

    public RistoranteBean() { /* COSTRUTTORE VUOTO */ }


    /* SETTER CON VALIDAZIONE */

    public void setPartitaIVA(String partitaIVA) throws ValidazioneException {
        if (partitaIVA == null || partitaIVA.trim().isEmpty()) {
            throw new ValidazioneException("La partita IVA non può essere vuota.");
        }
        if (!partitaIVA.matches("^0\\d{10}$")) {
            throw new ValidazioneException("La partita IVA deve contenere 11 cifre e iniziare con 0.");
        }
        this.partitaIVA = partitaIVA.trim();
    }

    public void setNome(String nome) throws ValidazioneException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidazioneException("Il nome del ristorante non può essere vuoto.");
        }
        if (!nome.matches("^[a-zA-Zàèéìòùçñ'\\s]+$")) {
            throw new ValidazioneException("Il nome può contenere solo lettere e spazi.");
        }
        this.nome = nome.trim();
    }

    public void setTelefono(String telefono) throws ValidazioneException {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new ValidazioneException("Il numero di telefono non può essere vuoto.");
        }
        if (!telefono.matches("^\\d{10,15}$")) {
            throw new ValidazioneException("Il telefono deve contenere solo numeri (10-15 cifre).");
        }
        this.telefono = telefono.trim();
    }

    public void setPosizioneRistorante(PosizioneBean posizioneRistorante) throws ValidazioneException {
        if (posizioneRistorante == null) {
            throw new ValidazioneException("La posizione del ristorante è obbligatoria.");
        }
        this.posizioneRistorante = posizioneRistorante;
    }

    public void setOffertaCulinaria(OffertaCulinariaBean offertaCulinaria) throws ValidazioneException {
        if (offertaCulinaria == null) {
            throw new ValidazioneException("L'offerta culinaria è obbligatoria.");
        }
        this.offertaCulinaria = offertaCulinaria;
    }

    public void setOrari(OrariBean orari) throws ValidazioneException {
        if (orari == null) {
            throw new ValidazioneException("Gli orari del ristorante sono obbligatori.");
        }
        this.orari = orari;
    }

    /* GETTER */
    public String getPartitaIVA() { return partitaIVA; }
    public String getNome() { return nome; }
    public String getTelefono() { return telefono; }
    public PosizioneBean getPosizioneRistorante() { return posizioneRistorante; }
    public OffertaCulinariaBean getOffertaCulinaria() { return offertaCulinaria; }
    public OrariBean getOrari() { return orari; }
}
