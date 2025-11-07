package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Recensione;

public interface RecensioneDAO {
    void nuovaRecensione(Recensione recensione) throws EccezioneDAO;
}
