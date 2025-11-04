package com.example.meteo_gusto.patterns.factory;

import com.example.meteo_gusto.dao.AmbienteDAO;
import com.example.meteo_gusto.dao.memoria.AmbienteDAOMemoria;
import com.example.meteo_gusto.dao.mysql.AmbienteDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class AmbienteDAOFactory {
    public AmbienteDAO getAmbienteDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV, MYSQL -> creaAmbienteDAOMYSQL();
            case MEMORIA -> creaAmbienteDAOMEMORIA();
        };
    }

    public AmbienteDAO creaAmbienteDAOMYSQL(){return new AmbienteDAOMySql();}
    public AmbienteDAO creaAmbienteDAOMEMORIA(){return new AmbienteDAOMemoria();}
}
