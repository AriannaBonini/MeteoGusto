package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreRistorante;
import java.util.List;

public class HomeUtenteController {
    private final DAOFactoryFacade daoFactoryFacade= DAOFactoryFacade.getInstance();
    private final RistoranteDAO ristoranteDAO= daoFactoryFacade.getRistoranteDAO();

    public List<RistoranteBean> trovaMiglioriRistoranti() throws ValidazioneException{
        try {
            return ConvertitoreRistorante.listaRistoranteModelInBean(ristoranteDAO.selezionaTop4RistorantiPerMedia());
        }catch (EccezioneDAO e) {
            throw new ValidazioneException("Errore durante la ricerca dei migliori ristoranti : " , e);
        }
    }

}

