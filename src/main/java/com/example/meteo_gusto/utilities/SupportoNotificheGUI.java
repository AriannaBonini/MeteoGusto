package com.example.meteo_gusto.utilities;

import javafx.scene.control.Label;

public final class SupportoNotificheGUI {

    private  SupportoNotificheGUI(){ /* COSTRUTTORE PRIVATO */ }

    public static void supportoNotifiche(Label notifichePrenotazione, Integer numeroNotifiche) {
        if(numeroNotifiche!=null && numeroNotifiche>0) {

            notifichePrenotazione.setVisible(true);
            notifichePrenotazione.setText(String.valueOf(numeroNotifiche));

        }else {
            notifichePrenotazione.setVisible(false);
        }

    }
}
