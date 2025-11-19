package com.example.meteo_gusto.utilities.supporto_componenti_gui;

import javafx.animation.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.security.SecureRandom;
import javafx.scene.Node;


public class SupportoGUIPaginaIniziale {

    private SupportoGUIPaginaIniziale() { /* COSTRUTTORE VUOTO */ }
    private static final SecureRandom random = new SecureRandom();

    /**
     * Fa apparire gradualmente un nodo (Label, VBox, ecc.) con fade-in.
     */
    public static void fadeInNode(Node node, double durataSecondi, double ritardoSecondi) {
        FadeTransition fade = new FadeTransition(Duration.seconds(durataSecondi), node);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setDelay(Duration.seconds(ritardoSecondi));
        fade.play();
    }

    /**
     * Aggiunge un effetto di piccole lucine lampeggianti sopra un nodo contenuto in uno StackPane.
     */
    public static void effettoLucine(Node nodo, int numeroLucine, int intervalloMillis) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(intervalloMillis), e -> {
            if (!(nodo.getParent() instanceof StackPane stack)) return;

            byte[] bytes = new byte[2];
            random.nextBytes(bytes);

            double width = nodo.getBoundsInParent().getWidth();
            double height = nodo.getBoundsInParent().getHeight();

            Circle luce = new Circle(2, Color.YELLOW);

            luce.setTranslateX((bytes[0] / 127.0) * (width / 2));
            luce.setTranslateY((bytes[1] / 127.0) * (height / 2));

            stack.getChildren().add(luce);

            FadeTransition fadeLuce = new FadeTransition(Duration.seconds(0.5), luce);
            fadeLuce.setFromValue(1.0);
            fadeLuce.setToValue(0.0);
            fadeLuce.setOnFinished(ev -> stack.getChildren().remove(luce));
            fadeLuce.play();
        }));

        timeline.setCycleCount(numeroLucine);
        timeline.play();
    }

    public static void animaSuggerimenti(double durataSecondi, VBox... schede) {
        if (schede == null || schede.length == 0) return;

        int[] indexVisibile = {0};

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(durataSecondi), e -> {
            for (int i = 0; i < schede.length; i++) {
                VBox v = schede[i];
                boolean isActive = i == indexVisibile[0];

                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), v);
                ft.setToValue(isActive ? 1.0 : 0.5);
                ft.play();

                ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), v);
                st.setToX(isActive ? 1.0 : 0.85);
                st.setToY(isActive ? 1.0 : 0.85);
                st.play();
            }

            indexVisibile[0] = (indexVisibile[0] + 1) % schede.length;
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }




    public static void nascondiNodi(Node... nodi) {
        for (Node nodo : nodi) {
            nodo.setOpacity(0.0);
        }

    }

    public static void suggerimentiOpachi(Node... nodi) {
        for (Node nodo: nodi) {
            nodo.setOpacity(0.5);
        }
    }

}
