package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.RecensioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Recensione;
import com.example.meteo_gusto.model.Ristorante;

public class RecensioneDAOMemoria implements RecensioneDAO {
    @Override
    public void nuovaRecensione(Recensione recensione) throws EccezioneDAO{}
    @Override
    public boolean esisteRecensione(Recensione recensione) throws EccezioneDAO {return true;}
    @Override
    public void aggiornaRecensione(Recensione recensione) throws EccezioneDAO{}
    @Override
    public Ristorante calcolaNuovaMedia(Recensione recensione) throws EccezioneDAO {return null;}
}
