package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ristorante;


public interface DietaDAO {
    void registraDieta(Ristorante ristorante) throws EccezioneDAO;
    Ristorante controllaDieteDelRistorante(Ristorante ristorante) throws EccezioneDAO;
}
