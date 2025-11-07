package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.GiorniEOrari;
import com.example.meteo_gusto.model.Ristorante;

public interface GiornoChiusuraDAO {

    void registraGiorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO;
    GiorniEOrari giorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO;
}
