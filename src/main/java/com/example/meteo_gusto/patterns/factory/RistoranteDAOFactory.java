package com.example.meteo_gusto.patterns.factory;

import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.dao.memoria.RistoranteDAOMemoria;
import com.example.meteo_gusto.dao.mysql.RistoranteDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class RistoranteDAOFactory {
    public RistoranteDAO getRistoranteDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV -> creaRistoranteDAOMYSQL();
            case MYSQL -> creaRistoranteDAOMYSQL();
            case MEMORIA -> creaRistoranteDAOMEMORIA();
        };
    }

    public PersonaDAO creaRistoranteDAOMYSQL(){return new RistoranteDAOMySql();}
    public PersonaDAO creaRistoranteDAOMEMORIA(){return new RistoranteDAOMemoria();}
}
