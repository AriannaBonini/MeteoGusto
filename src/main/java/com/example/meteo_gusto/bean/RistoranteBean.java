package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class RistoranteBean {
    private String partitaIVA;
    private String nome;
    private String telefono;
    private PosizioneBean posizioneRistorante;
    private OffertaCulinariaBean offertaCulinaria;
    private OrariBean orari;


    /* COSTRUTTORE CON PARAMETRI */
    public RistoranteBean(String partitaIVA, String nome, String telefono, OrariBean orari, OffertaCulinariaBean offertaCulinaria, PosizioneBean posizioneRistorante) throws ValidazioneException{
        validaCampiNonVuoti(partitaIVA,nome,telefono);
        validaNome(nome);
        validaTelefono(telefono);
        validaPartitaIVA(partitaIVA);

        this.partitaIVA=partitaIVA;
        this.nome=nome;
        this.telefono=telefono;
        this.orari = orari;
        this.offertaCulinaria=offertaCulinaria;
        this.posizioneRistorante=posizioneRistorante;
    }


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCampiNonVuoti(String partitaIVA, String nome, String telefono) throws ValidazioneException {
        if (partitaIVA == null || partitaIVA.trim().isEmpty() ||
                nome == null || nome.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty()) {
            throw new ValidazioneException("Tutti i campi sono obbligatori");
        }
    }

    private void validaNome(String nome) throws ValidazioneException {
        if (!nome.matches("^[a-zA-Zàèéìòùçñ']+$")) {
            throw new ValidazioneException("Il nome può contenere solo lettere (senza spazi)");
        }
    }

    private void validaTelefono(String telefono) throws ValidazioneException {
        if (!telefono.matches("^\\d{10,15}$")) {
            throw new ValidazioneException("Il telefono deve contenere solo numeri (10-15 cifre)");
        }
    }

    public static void validaPartitaIVA(String partitaIVA) throws ValidazioneException {
        if (partitaIVA == null || !partitaIVA.matches("^0\\d{10}$")) {
            throw new ValidazioneException("La partita IVA deve contenere 11 cifre e iniziare con 0");
        }
    }

    /* METODI GETTER E SETTER */
    public void setPartitaIVA(String partitaIVA) {this.partitaIVA = partitaIVA;}
    public void setNome(String nome) {this.nome = nome;}
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public OrariBean getOrari() {return orari;}
    public void setOrari(OrariBean orari) {this.orari = orari;}
    public PosizioneBean getPosizioneRistorante() {return posizioneRistorante;}
    public void setPosizioneRistorante(PosizioneBean posizioneRistorante) {this.posizioneRistorante = posizioneRistorante;}
    public OffertaCulinariaBean getOffertaCulinaria() {return offertaCulinaria;}
    public void setOffertaCulinaria(OffertaCulinariaBean offertaCulinaria) {this.offertaCulinaria = offertaCulinaria;}
    public String getPartitaIVA() {return partitaIVA;}
    public String getNome() {return nome;}
    public String getTelefono() {return telefono;}
}
