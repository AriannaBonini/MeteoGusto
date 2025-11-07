package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Filtro;
import com.example.meteo_gusto.model.Ristorante;

import java.util.List;

public class RistoranteDAOMemoria implements RistoranteDAO {

    @Override
    public void registraRistorante(Ristorante ristorante) {}

    @Override
    public List<Ristorante> filtraRistorantiPerCitta(Filtro filtro) throws EccezioneDAO { return null;}
    @Override
    public Ristorante mediaStelleRistorante(Ristorante ristorante) throws EccezioneDAO{return null;}
    @Override
    public void aggiornaMediaStelle(Ristorante ristorante) throws EccezioneDAO {}
}
