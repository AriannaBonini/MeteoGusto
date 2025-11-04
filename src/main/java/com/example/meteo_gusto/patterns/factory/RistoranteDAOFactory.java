package com.example.meteo_gusto.patterns.factory;

import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.dao.memoria.RistoranteDAOMemoria;
import com.example.meteo_gusto.dao.mysql.RistoranteDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class RistoranteDAOFactory {
    public RistoranteDAO getRistoranteDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV, MYSQL -> creaRistoranteDAOMYSQL();
            case MEMORIA -> creaRistoranteDAOMEMORIA();
        };
    }

    public RistoranteDAO creaRistoranteDAOMYSQL(){return new RistoranteDAOMySql();}
    public RistoranteDAO creaRistoranteDAOMEMORIA(){return new RistoranteDAOMemoria();}
}
