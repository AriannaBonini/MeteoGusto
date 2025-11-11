package com.example.meteo_gusto.dao.memoria;


import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;

public class PersonaDAOMemoria implements PersonaDAO {

    @Override
    public void registraPersona(Persona utente) throws EccezioneDAO {
    }

    @Override
    public Persona login(Persona persona) throws EccezioneDAO {return null;}
    @Override
    public Persona informazioniUtente(Persona utente) throws EccezioneDAO {return null;}




}
