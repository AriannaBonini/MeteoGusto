package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Prenotazione;

public interface PrenotazioneDAO {

    Prenotazione postiOccupatiPerDataEFasciaOraria(Prenotazione prenotazione) throws EccezioneDAO;
}
