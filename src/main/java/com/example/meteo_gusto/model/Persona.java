package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.TipoPersona;

public class Persona {
    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private String password;
    private TipoPersona tipoPersona;

    public Persona(String nome, String cognome, String telefono, String email, String password, TipoPersona tipoPersona) {
       this.nome=nome;
       this.cognome=cognome;
       this.telefono=telefono;
       this.email=email;
       this.password=password;
       this.tipoPersona=tipoPersona;
    }

    /* COSTRUTTORE PER IL LOGIN */
    public Persona(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public  String getCognome() {return cognome;}
    public void setCognome(String cognome) {this.cognome = cognome;}
    public  String getTelefono() {return telefono;}
    public  void setTelefono(String telefono) {this.telefono = telefono;}
    public  String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public  String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public TipoPersona getTipoPersona() {return tipoPersona;}


    /* METODI DI LOGICA DI DOMINIO */
    public boolean tipoUtente() {return tipoPersona==TipoPersona.UTENTE;}
    public boolean tipoRistoratore() {return tipoPersona==TipoPersona.RISTORATORE;}

}


