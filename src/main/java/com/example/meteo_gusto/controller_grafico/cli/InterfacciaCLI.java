package com.example.meteo_gusto.controller_grafico.cli;

import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;

public interface InterfacciaCLI {
    void start();
    int mostraMenu() throws ValidazioneException, EccezioneDAO;
}

