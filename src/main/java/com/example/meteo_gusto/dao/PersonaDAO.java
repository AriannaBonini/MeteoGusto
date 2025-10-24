package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;

public interface PersonaDAO {
    void registraUtente(Persona utente) throws EccezioneDAO;
}
