package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;

public interface PrenotazioneDAO {

    Prenotazione postiOccupatiPerDataEFasciaOraria(Prenotazione prenotazione) throws EccezioneDAO;
    boolean inserisciPrenotazione(Prenotazione prenotazione) throws EccezioneDAO;
    boolean esistePrenotazione(Prenotazione prenotazione) throws EccezioneDAO;
    Prenotazione contaNotificheAttiveUtente(Persona utente) throws EccezioneDAO ;
}
