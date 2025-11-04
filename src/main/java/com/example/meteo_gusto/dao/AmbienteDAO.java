package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Ristorante;

import java.util.List;

public interface AmbienteDAO {
    void registraDisponibilita(List<Ambiente> ambiente) throws EccezioneDAO;
    List<Ambiente> cercaAmbientiDelRistorante(Ristorante ristorante) throws EccezioneDAO;
}
