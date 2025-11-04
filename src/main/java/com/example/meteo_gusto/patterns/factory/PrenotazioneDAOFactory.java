package com.example.meteo_gusto.patterns.factory;


import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.dao.memoria.PrenotazioneDAOMemoria;
import com.example.meteo_gusto.dao.mysql.PrenotazioneDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class PrenotazioneDAOFactory {
    public PrenotazioneDAO getPrenotazioneDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV, MYSQL -> creaPrenotazioneDAOMYSQL();
            case MEMORIA -> creaPrenotazioneDAOMEMORIA();
        };
    }

    public PrenotazioneDAO creaPrenotazioneDAOMYSQL(){return new PrenotazioneDAOMySql();}
    public PrenotazioneDAO creaPrenotazioneDAOMEMORIA(){return new PrenotazioneDAOMemoria();}
}
