package com.example.meteo_gusto.model;


import java.math.BigDecimal;

public class Ristorante {
    private String partitaIVA;
    private Persona proprietario;
    private String nome;
    private String telefono;
    private Posizione posizione;
    private OffertaCulinaria offertaCulinaria;
    private Orari orari;
    private BigDecimal mediaStelle;

    /* COSTRUTTORE CON PARAMETRI */
    public Ristorante(String partitaIVA, Persona proprietario, String nome, String telefono, Orari orari, OffertaCulinaria offertaCulinaria, Posizione posizione) {
        this.partitaIVA=partitaIVA;
        this.proprietario=proprietario;
        this.nome=nome;
        this.telefono=telefono;
        this.orari = orari;
        this.offertaCulinaria=offertaCulinaria;
        this.posizione=posizione;
    }



    /* METODI GETTER E SETTER */
    public void setPartitaIVA(String partitaIVA) {this.partitaIVA = partitaIVA;}
    public void setNome(String nome) {this.nome = nome;}
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public String getPartitaIVA() {return partitaIVA;}
    public String getNome() {return nome;}
    public String getTelefono() {return telefono;}
    public Orari getOrari() {return orari;}
    public void setOrari(Orari orari) {this.orari = orari;}
    public Posizione getPosizione() {return posizione;}
    public void setPosizione(Posizione posizione) {this.posizione = posizione;}
    public OffertaCulinaria getOffertaCulinaria() {return offertaCulinaria;}
    public void setOffertaCulinaria(OffertaCulinaria offertaCulinaria) {this.offertaCulinaria = offertaCulinaria;}
    public Persona getProprietario() {return proprietario;}
    public void setProprietario(Persona proprietario) {this.proprietario = proprietario;}
    public BigDecimal getMediaStelle() {return mediaStelle;}
    public void setMediaStelle(BigDecimal mediaStelle) {this.mediaStelle = mediaStelle;}
}
