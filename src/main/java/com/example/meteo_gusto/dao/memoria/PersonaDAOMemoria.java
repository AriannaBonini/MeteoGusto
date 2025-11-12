package com.example.meteo_gusto.dao.memoria;


import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;

import java.util.HashMap;
import java.util.Map;

public class PersonaDAOMemoria implements PersonaDAO {

    private final Map<String, Persona> personeMap = new HashMap<>();

    @Override
    public void registraPersona(Persona utente) throws EccezioneDAO {
        try {
            if (utente == null || utente.getEmail() == null || utente.getEmail().isEmpty()) {
                throw new EccezioneDAO("L'utente o l'email non è valorizzata.");
            }

            if (personeMap.containsKey(utente.getEmail())) {
                throw new EccezioneDAO("Esiste già un utente registrato con questa email: " + utente.getEmail());
            }


            personeMap.put(utente.getEmail(), utente);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la registrazione dell'utente in memoria", e);
        }
    }


    @Override
    public Persona login(Persona persona) throws EccezioneDAO {
        try {
            if (persona == null || persona.getEmail() == null || persona.getPassword() == null) {
                throw new EccezioneDAO("Email o password non valorizzate.");
            }

            Persona personaTrovata = personeMap.get(persona.getEmail());
            if (personaTrovata != null && personaTrovata.getPassword().equals(persona.getPassword())) {
                return personaTrovata;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il login dell'utente in memoria", e);
        }
    }



    @Override
    public Persona informazioniUtente(Persona utente) throws EccezioneDAO {
        try {
            if (utente == null || utente.getEmail() == null) {
                throw new EccezioneDAO("Email utente non valorizzata.");
            }

            Persona personaTrovata = personeMap.get(utente.getEmail());
            if (personaTrovata != null) {

                Persona info = new Persona();
                info.setNome(personaTrovata.getNome());
                info.setCognome(personaTrovata.getCognome());
                info.setTelefono(personaTrovata.getTelefono());
                info.setEmail(personaTrovata.getEmail());
                return info;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero dei dati dell'utente in memoria", e);
        }
    }





}
