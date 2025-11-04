package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.GiornoChiusura;
import com.example.meteo_gusto.model.Ristorante;
import java.util.List;

public interface GiornoChiusuraDAO {

    void registraGiorniChiusuraRistorante(List<GiornoChiusura> giornoChiusura) throws EccezioneDAO;
    List<GiornoChiusura> giorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO;
}
