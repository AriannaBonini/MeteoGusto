module com.example.meteo_gusto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires wiremock.jre8;
    requires java.net.http;
    requires org.json;


    opens com.example.meteo_gusto to javafx.fxml;
    exports com.example.meteo_gusto;
    exports com.example.meteo_gusto.eccezione;
    exports com.example.meteo_gusto.patterns.facade;
    exports com.example.meteo_gusto.model;
    exports com.example.meteo_gusto.enumerazione;
    exports com.example.meteo_gusto.dao;
    exports com.example.meteo_gusto.controller;
    exports com.example.meteo_gusto.bean;
    exports com.example.meteo_gusto.utilities.convertitore;



    opens com.example.meteo_gusto.controller_grafico.gui.ristoratore to javafx.fxml;
    opens com.example.meteo_gusto.controller_grafico.gui.utente to javafx.fxml;
    opens com.example.meteo_gusto.controller_grafico.gui to javafx.fxml;
}