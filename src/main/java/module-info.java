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
    opens com.example.meteo_gusto.controller_grafico.gui.ristoratore to javafx.fxml;
    opens com.example.meteo_gusto.controller_grafico.gui.utente to javafx.fxml;
    opens com.example.meteo_gusto.controller_grafico.gui to javafx.fxml;
}