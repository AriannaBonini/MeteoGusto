package com.example.meteo_gusto.utilities;

import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import java.time.DayOfWeek;
import java.time.LocalDate;

public final class GiorniSettimanaHelper {

    private GiorniSettimanaHelper(){ /* COSTRUTTORE VUOTO */ }

    public static GiorniSettimana dataInGiornoSettimana(LocalDate data) {
        if (data == null) return null;

        DayOfWeek dayOfWeek = data.getDayOfWeek();

        return switch (dayOfWeek) {
            case MONDAY    -> GiorniSettimana.LUNEDI;
            case TUESDAY   -> GiorniSettimana.MARTEDI;
            case WEDNESDAY -> GiorniSettimana.MERCOLEDI;
            case THURSDAY  -> GiorniSettimana.GIOVEDI;
            case FRIDAY    -> GiorniSettimana.VENERDI;
            case SATURDAY  -> GiorniSettimana.SABATO;
            case SUNDAY    -> GiorniSettimana.DOMENICA;
        };
    }

}
