package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ristorante;

public interface RistoranteDAO {
    void registraRistorante(Ristorante ristorante) throws EccezioneDAO;
}
