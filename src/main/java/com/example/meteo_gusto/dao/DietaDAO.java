package com.example.meteo_gusto.dao;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Dieta;
import java.util.List;

public interface DietaDAO {
    void registraDieta(List<Dieta> dieta) throws EccezioneDAO;
    Dieta controllaDieteDelRistorante(Dieta filtroDieta) throws EccezioneDAO;
}
