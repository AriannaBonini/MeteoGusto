package com.example.meteo_gusto.utilities.supporto_gui;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;

import java.util.List;

public class SupportoCheckBoxCss {

    private SupportoCheckBoxCss() { /* Costruttore vuoto */ }

    /** Inizializza un singolo CheckBox */
    public static void inizializzaCheckBox(CheckBox checkBox) {
        checkBox.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                Platform.runLater(() -> applicaStile(checkBox));
            }
        });

        checkBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> applicaStile(checkBox));
            }
        });

    }

    /** Inizializza più CheckBox in un colpo solo */
    public static void inizializzaCheckBoxMultipli(List<CheckBox> checkBoxes) {
        for (CheckBox cb : checkBoxes) {
            inizializzaCheckBox(cb);
        }
    }

    /** Applica la classe CSS una sola volta */
    private static void applicaStile(CheckBox checkBox) {
        if (!checkBox.getStyleClass().contains("check-box-arancione")) {
            checkBox.getStyleClass().add("check-box-arancione");
        }
    }
}

