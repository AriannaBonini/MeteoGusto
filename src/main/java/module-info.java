module com.example.meteo_gusto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;

    opens com.example.meteo_gusto to javafx.fxml;
    opens com.example.meteo_gusto.controller_grafico to javafx.fxml;
    exports com.example.meteo_gusto;
}