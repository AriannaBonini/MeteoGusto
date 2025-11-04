package com.example.meteo_gusto.patterns.factory;

import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.memoria.PersonaDAOMemoria;
import com.example.meteo_gusto.dao.mysql.PersonaDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class PersonaDAOFactory {

    public PersonaDAO getPersonaDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV, MYSQL -> creaPersonaDAOMYSQL();
            case MEMORIA -> creaPersonaDAOMEMORIA();
        };
    }

    public PersonaDAO creaPersonaDAOMYSQL(){return new PersonaDAOMySql();}
    public PersonaDAO creaPersonaDAOMEMORIA(){return new PersonaDAOMemoria();}

}
