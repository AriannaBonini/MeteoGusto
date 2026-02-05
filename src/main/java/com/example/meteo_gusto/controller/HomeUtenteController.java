package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreRistorante;
import java.util.List;

public class HomeUtenteController {

    public List<RistoranteBean> trovaMiglioriRistoranti() throws ValidazioneException{
        try {

            return ConvertitoreRistorante.miglioriQuattroRistorantiInBean(DAOFactoryFacade.getInstance().getRistoranteDAO().selezionaTop4RistorantiPerMedia());
        }catch (EccezioneDAO e) {
            throw new ValidazioneException("Errore durante la ricerca dei migliori ristoranti : " , e);
        }
    }

}

