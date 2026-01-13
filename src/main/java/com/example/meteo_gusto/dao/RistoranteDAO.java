package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Filtro;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Ristorante;
import java.util.List;

public interface RistoranteDAO {
    void registraRistorante(Ristorante ristorante) throws EccezioneDAO;
    List<Ristorante> filtraRistorantiPerCitta(Filtro filtro) throws EccezioneDAO;
    Ristorante mediaStelleRistorante(Ristorante ristorante) throws EccezioneDAO;
    void aggiornaMediaStelle(Ristorante ristorante) throws EccezioneDAO;
    Ristorante selezionaInfoRistorante(Ambiente ambiente) throws EccezioneDAO;
    Ristorante selezionaRistorantePerProprietario(Persona ristoratore) throws EccezioneDAO;
    List<Ristorante> selezionaTop4RistorantiPerMedia() throws EccezioneDAO;
    Ristorante dettagliRistorante(Ristorante ristorante) throws EccezioneDAO;
}
