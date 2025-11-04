package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.GiornoChiusuraDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.GiornoChiusura;
import com.example.meteo_gusto.model.Ristorante;

import java.util.List;

public class GiornoChiusuraDAOMemoria implements GiornoChiusuraDAO {
    @Override
    public void registraGiorniChiusuraRistorante(List<GiornoChiusura> giornoChiusura) throws EccezioneDAO {}

    @Override
    public List<GiornoChiusura> giorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {return null;}
}
