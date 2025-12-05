package com.example.meteo_gusto.utilities.supporto_gui;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

public class SupportoScrollPaneCss {

    private SupportoScrollPaneCss() {/* Costruttore vuoto*/}

    /**
     * Collega i listener e applica le classi CSS personalizzate
     * alle scrollbar verticali e orizzontali di uno ScrollPane.
     */
    public static void inizializzaScrollPane(ScrollPane scrollPane) {
        // Listener: quando lo ScrollPane entra in scena
        scrollPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> applicaStile(scrollPane));
            }
        });

        // Listener: se il skin viene ricreato
        scrollPane.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                Platform.runLater(() -> applicaStile(scrollPane));
            }
        });
    }

    /** Applica una volta le classi CSS alle scrollbar */
    private static void applicaStile(ScrollPane scrollPane) {
        for (Node node : scrollPane.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar sb) {
                if (sb.getOrientation() == Orientation.VERTICAL) {
                    aggiungiClasseUnaVolta(sb, "scroll-bar-vertical");
                } else if (sb.getOrientation() == Orientation.HORIZONTAL) {
                    aggiungiClasseUnaVolta(sb, "scroll-bar-horizontal");
                }
            }
        }
    }

    /** Aggiunge la classe CSS solo se non è già presente */
    private static void aggiungiClasseUnaVolta(ScrollBar sb, String nomeClasse) {
        if (!sb.getStyleClass().contains(nomeClasse)) {
            sb.getStyleClass().add(nomeClasse);
        }
    }
}
