package com.example.meteo_gusto;


import java.util.Scanner;

import com.example.meteo_gusto.mockapi.MockMeteoAPI;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;
import javafx.stage.Stage;
import javafx.application.Application;
import com.example.meteo_gusto.controller_grafico.GestoreScena;

import static java.lang.System.*;

public class Main extends Application {


    @Override
    public void start(Stage stage)  {
        GestoreScena.cambiaScenaSenzaEvento("/Login.fxml", stage);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);
        String messaggio= "Inserisci 1 o 2: ";

        out.println("🌤️  BENVENUTO IN METEOGUSTO!  🌤️");

        out.println("Quale interfaccia desideri?");
        out.println("1. Interfaccia grafica (GUI)");
        out.println("2. Interfaccia a riga di comando (CLI)");
        out.print(messaggio);
        boolean tipoInterfaccia = scanner.nextInt() == 1;
        scanner.nextLine();


        out.println("\nQuale versione desideri?");
        out.println("1. Demo ");
        out.println("2. Versione completa ");
        out.print(messaggio);
        boolean tipoVersione = scanner.nextInt() == 2;
        scanner.nextLine();


        if (tipoVersione) {
            out.println("\nQuale tipo di persistenza desideri?");
            out.println("1. Database (MySQL)");
            out.println("2. File system (CSV)");
            out.print(messaggio);
            int scelta = scanner.nextInt();
            scanner.nextLine();

            if (scelta == 1) {
                DAOFactoryFacade.getInstance().setTipoPersistenza(TipoPersistenza.MYSQL);
            } else {
                DAOFactoryFacade.getInstance().setTipoPersistenza(TipoPersistenza.CSV);
            }
        } else {
            out.print("sono in memoria");
            DAOFactoryFacade.getInstance().setTipoPersistenza(TipoPersistenza.MEMORIA);
        }

        MockMeteoAPI.startMock(8080);

        try {
            if (tipoInterfaccia) {
                out.println("\nAvvio interfaccia grafica...");
                launch(args); // JavaFX
            } else {
                out.println("\nAvvio interfaccia a riga di comando...");
                // MeteoGustoCLI cli = new MeteoGustoCLI(scanner);
                // cli.start();
            }
        } finally {
            MockMeteoAPI.stopMock();
            scanner.close();
        }
    }
}

