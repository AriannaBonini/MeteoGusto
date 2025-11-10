package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;

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
}
