package com.example.meteo_gusto.controller_grafico.cli.ristoratore;

import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;

import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class HomeRistoratoreCliCG implements InterfacciaCLI {

    @Override
    public void start() {
        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> esci=true;
                    case 2 -> esci=true;
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e ) {
                GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
            }
        }
        System.exit(0);
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.mostraGraficaMenu("BENVENUTO SU METEOGUSTO", "Login", "Registrazione");
        return opzioneScelta(1,3);
    }
}
