package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.AmbienteDisponibile;

public interface DisponibilitaDAO {
    void registraDisponibilita(AmbienteDisponibile ambienteDisponibile) throws EccezioneDAO;
}
