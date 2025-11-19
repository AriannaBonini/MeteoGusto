package com.example.meteo_gusto.sessione;

import com.example.meteo_gusto.model.Persona;

public class Sessione {
    private static Sessione istanza =null;
    private Persona persona;

    private Sessione() {}

    public static synchronized Sessione getInstance() {
        if (istanza == null) {
            istanza = new Sessione();
        }
        return istanza;
    }
    public void login(Persona persona) {this.persona = persona;}
    public Persona getPersona() {return persona;}
    public void logout() {persona = null;}
}
