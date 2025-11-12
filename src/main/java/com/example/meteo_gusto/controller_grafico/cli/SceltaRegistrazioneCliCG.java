package com.example.meteo_gusto.controller_grafico.cli;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;

import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class SceltaRegistrazioneCliCG implements InterfacciaCLI {
    @Override
    public void start() {
        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> registrazioneUtente();
                    case 2 -> registrazioneRistoratore();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
            }
        }
        new LoginCliCG().start();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.mostraGraficaMenu("COME DESIDERI REGISTRARTI ", "Utente", "Ristoratore", "Torna alla schermata iniziale");
        return opzioneScelta(1,3);
    }

    private void registrazioneUtente(){}
    private void registrazioneRistoratore(){}
}
