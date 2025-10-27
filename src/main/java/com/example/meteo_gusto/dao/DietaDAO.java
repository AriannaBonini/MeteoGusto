package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Dieta;

public interface DietaDAO {
    void registraDieta(Dieta dieta) throws EccezioneDAO;
}
