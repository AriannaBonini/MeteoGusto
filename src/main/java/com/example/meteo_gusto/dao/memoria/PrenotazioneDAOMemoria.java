package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;

import java.util.List;

public class PrenotazioneDAOMemoria implements PrenotazioneDAO {

    @Override
    public Prenotazione postiOccupatiPerDataEFasciaOraria(Prenotazione prenotazione) throws EccezioneDAO{
        return null;
    }

    @Override
    public boolean inserisciPrenotazione(Prenotazione prenotazione) throws EccezioneDAO {return true;}
    @Override
    public boolean esistePrenotazione(Prenotazione prenotazione) throws EccezioneDAO {return true;}
    @Override
    public Prenotazione contaNotificheAttiveUtente(Persona utente) throws EccezioneDAO {return null;}
    @Override
    public void resettaNotificheUtente(Persona utente) throws EccezioneDAO {}
    @Override
    public List<Prenotazione> selezionaPrenotazioniUtente(Persona utente) throws EccezioneDAO {return null;}

    @Override
    public List<Prenotazione> selezionaPrenotazioniRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {return null;}


}
