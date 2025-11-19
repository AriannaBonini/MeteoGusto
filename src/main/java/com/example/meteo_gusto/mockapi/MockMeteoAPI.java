package com.example.meteo_gusto.mockapi;


import java.util.concurrent.ThreadLocalRandom;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.extension.Parameters;

public class MockMeteoAPI {

    private MockMeteoAPI() {}

    private static WireMockServer wireMockServer;

    public static void startMock(int port) {
        wireMockServer = new WireMockServer(WireMockConfiguration.options()
                .port(port)
                .extensions(new MeteoResponseTransformer()));

        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/meteo"))
                .willReturn(aResponse()
                        .withTransformers("meteo-transformer")));
    }

    public static void stopMock() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    public static class MeteoResponseTransformer extends ResponseTransformer {

        private static final String[] CONDIZIONI_METEO = {"Sole", "Pioggia", "Nuvoloso"};

        @Override
        public Response transform(Request request, Response response, FileSource files, Parameters parameters) {

            String condizione = CONDIZIONI_METEO[ThreadLocalRandom.current().nextInt(CONDIZIONI_METEO.length)];
            int temperatura = 5 + ThreadLocalRandom.current().nextInt(31);

            String body = String.format("{ \"temperatura\": %d, \"condizione\": \"%s\" }", temperatura, condizione);

            return Response.response()
                    .body(body)
                    .headers(response.getHeaders())
                    .status(200)
                    .build();
        }

        @Override
        public String getName() {
            return "meteo-transformer";
        }

        @Override
        public boolean applyGlobally() {
            return false;
        }
    }

}

