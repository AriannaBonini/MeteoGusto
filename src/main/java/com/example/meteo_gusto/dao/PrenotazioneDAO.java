package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;

import java.util.List;

public interface PrenotazioneDAO {

    Prenotazione postiOccupatiPerDataEFasciaOraria(Prenotazione prenotazione) throws EccezioneDAO;
    boolean inserisciPrenotazione(Prenotazione prenotazione) throws EccezioneDAO;
    boolean esistePrenotazione(Prenotazione prenotazione) throws EccezioneDAO;
    Prenotazione contaNotificheAttiveUtente(Persona utente) throws EccezioneDAO ;
    void resettaNotificheUtente(Persona utente) throws EccezioneDAO;
    List<Prenotazione> selezionaPrenotazioniUtente(Persona utente) throws EccezioneDAO;
    List<Prenotazione> selezionaPrenotazioniRistoratore(Ambiente ambiente) throws EccezioneDAO;
    Prenotazione contaNotificheAttiveRistoratore(List<Ambiente> ambienti) throws EccezioneDAO;
    void resettaNotificheRistoratore(List<Ambiente> ambienti) throws EccezioneDAO;
}
