package com.example.meteo_gusto.mockapi;



import com.example.meteo_gusto.bean.MeteoBean;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;



public class BoundaryMeteoMockAPI {

    private static final Logger logger = LoggerFactory.getLogger(BoundaryMeteoMockAPI.class);
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private BoundaryMeteoMockAPI() {}

    public static MeteoBean getMeteoDaMockAPI() throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/meteo"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IOException("Errore HTTP: " + response.statusCode());
            }

            JSONObject json = new JSONObject(response.body());
            int temperatura = json.getInt("temperatura");
            String condizione = json.getString("condizione");

            logger.info("Dati meteo ricevuti da Mock API: {} - {}°C", condizione, temperatura);
            return new MeteoBean(temperatura, condizione);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrotto durante la chiamata alla Mock API", e);
            throw new InterruptedIOException("Thread interrotto durante la chiamata alla Mock API");
        } catch (IOException | JSONException e) {
            logger.error("Errore generico nella chiamata alla Mock API: {}", e.getMessage(), e);
            throw new IOException("Errore nella chiamata alla Mock API", e);
        }
    }
}


