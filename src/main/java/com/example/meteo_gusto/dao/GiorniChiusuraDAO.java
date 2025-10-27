package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.GiorniChiusura;

public interface GiorniChiusuraDAO {

    void registraGiorniChiusuraRistorante(GiorniChiusura giorniChiusura) throws EccezioneDAO;
}
