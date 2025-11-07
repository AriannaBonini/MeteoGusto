package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.DietaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ristorante;


public class DietaDAOMemoria implements DietaDAO {

    @Override
    public void registraDieta(Ristorante ristorante) throws EccezioneDAO {}

    @Override
    public Ristorante controllaDieteDelRistorante(Ristorante ristorante) throws EccezioneDAO{return null;}
}
