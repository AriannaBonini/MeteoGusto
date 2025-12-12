package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.TipoPersona;

public class Persona {
    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private String password;
    private TipoPersona tipoPersona;
    private Ristorante ristorante;

    /* COSTRUTTORE PER LA REGISTRAZIONE*/
    public Persona(String nome, String cognome, String telefono, String email, String password, TipoPersona tipoPersona, Ristorante ristorante) {
       this.nome=nome;
       this.cognome=cognome;
       this.telefono=telefono;
       this.email=email;
       this.password=password;
       this.tipoPersona=tipoPersona;
       this.ristorante=ristorante;
    }

    public Persona() { /* COSTRUTTORE VUOTO */ }



    /* METODI GETTER E SETTER */
    public Persona(String email) {this.email=email;}
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
    public Ristorante getRistorante() {return ristorante;}
    public void setTipoPersona(TipoPersona tipoPersona) {this.tipoPersona = tipoPersona;}

    public void ristoranteDiProprieta(Ristorante ristorante) {
        if (tipoPersona.equals(TipoPersona.RISTORATORE)) {
            this.ristorante = ristorante;
        } else {
            this.ristorante = null;
        }
    }


}


