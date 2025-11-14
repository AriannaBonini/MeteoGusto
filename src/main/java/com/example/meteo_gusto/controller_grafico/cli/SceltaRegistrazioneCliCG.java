package com.example.meteo_gusto.controller_grafico.cli;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;

import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class SceltaRegistrazioneCliCG implements InterfacciaCLI {
    @Override
    public void start() {
        GestoreOutput.separatoreArancione();


        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> GestoreScenaCLI.vaiAllaRegistrazioneUtente();
                    case 2 -> GestoreScenaCLI.vaiAllaRegistrazioneRistoratore();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
            }
        }
        GestoreScenaCLI.login();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("REGISTRAZIONE ");
        GestoreOutput.mostraGraficaMenu("Registrati come utente", "Registrati come ristoratore", "Torna al login");
        return opzioneScelta(1,3);
    }

}
