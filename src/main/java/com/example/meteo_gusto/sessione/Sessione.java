package com.example.meteo_gusto.sessione;

import com.example.meteo_gusto.model.Persona;

public class Sessione {
    private static Sessione instance;
    private Persona persona;

    private Sessione() {}

    public static Sessione getInstance() {
        if (instance == null) {
            instance = new Sessione();
        }
        return instance;
    }
    public void login(Persona persona) {this.persona = persona;}
    public Persona getPersona() {return persona;}
    public void logout() {persona = null;}
    public boolean tipoUtente() {return persona != null && persona.tipoUtente();}
    public boolean tipoRistoratore() {return persona != null && persona.tipoRistoratore();}
}
