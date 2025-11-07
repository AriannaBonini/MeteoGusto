package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.GiornoChiusuraDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.GiorniEOrari;
import com.example.meteo_gusto.model.Ristorante;


public class GiornoChiusuraDAOMemoria implements GiornoChiusuraDAO {
    @Override
    public void registraGiorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {}

    @Override
    public GiorniEOrari giorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {return null;}
}
