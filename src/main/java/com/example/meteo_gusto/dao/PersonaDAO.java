package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;

public interface PersonaDAO {
    void registraPersona(Persona utente) throws EccezioneDAO;
    Persona login(Persona persona) throws EccezioneDAO;
    Persona informazioniUtente(Persona utente) throws EccezioneDAO;

}
