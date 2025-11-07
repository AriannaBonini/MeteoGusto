package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Recensione;
import com.example.meteo_gusto.model.Ristorante;

public interface RecensioneDAO {
    void nuovaRecensione(Recensione recensione) throws EccezioneDAO;
    boolean esisteRecensione(Recensione recensione) throws EccezioneDAO;
    void aggiornaRecensione(Recensione recensione) throws EccezioneDAO;
    Ristorante calcolaNuovaMedia(Recensione recensione) throws EccezioneDAO;
}
