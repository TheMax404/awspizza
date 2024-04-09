package it.adesso.awspizza.service;

import it.adesso.awspizza.connector.*;
import it.adesso.awspizza.exception.BadRequestErrorException;
import it.adesso.awspizza.exception.InternalServerErrorException;
import it.adesso.awspizza.model.InfoOrdineModel;
import it.adesso.awspizza.model.OrdineModel;
import it.adesso.awspizza.model.StatoEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzeriaService {

    Logger logger = LogManager.getLogger(PizzeriaService.class);

    private final NextOrdineConnector nextOrdineConnector;
    private final PutOrdineConnector putOrdineConnector;
    private final InfoOrdineConnector infoOrdineConnector;
    private final ModificaStatoOrdineConnector modificaStatoOrdineConnector;
    private final GetOrdiniConnector getOrdiniConnector;

    @Autowired
    public PizzeriaService(NextOrdineConnector nextOrdineConnector,
                           PutOrdineConnector putOrdineConnector,
                           InfoOrdineConnector infoOrdineConnector,
                           ModificaStatoOrdineConnector modificaStatoOrdineConnector,
                           GetOrdiniConnector getOrdiniConnector) {
        this.nextOrdineConnector = nextOrdineConnector;
        this.putOrdineConnector = putOrdineConnector;
        this.infoOrdineConnector = infoOrdineConnector;
        this.modificaStatoOrdineConnector = modificaStatoOrdineConnector;
        this.getOrdiniConnector = getOrdiniConnector;
    }


    public List<OrdineModel> getOrdini(List<StatoEnum> stato){
        List<OrdineModel> response;
        try{
            response = getOrdiniConnector.call(stato);
        }catch (Exception e){
            logger.error("Exception in getOrdini", e);
            throw new InternalServerErrorException("Internal error");
        }
        return response;
    }

    public List<InfoOrdineModel> putOrdine(Long idOrdine){
        List<InfoOrdineModel> response = new ArrayList<>();
        try{
        idOrdine = putOrdineConnector.call(idOrdine, StatoEnum.IN_PROGRESS.toString());
        }catch (Exception e){
            logger.error("Exception in getNextOrder", e);
            throw new InternalServerErrorException("Internal error");
        }
        if(idOrdine == -1) {
            logger.error("Non è possibile procedere con questo ordine");
            throw new BadRequestErrorException("Non è possibile procedere con questo ordine");
        }
            try {
                response = infoOrdineConnector.getInfoOrdine(idOrdine);
            }catch (Exception e ){
                logger.error("Exception in infoOrderConnector", e);
                throw new InternalServerErrorException();
            }
        return response;
    }

    public List<InfoOrdineModel> getNextOrdine(){
        long idOrdine = -1;
        List<InfoOrdineModel> response = new ArrayList<>();
        try{
            idOrdine = nextOrdineConnector.call(StatoEnum.IN_PROGRESS.toString());
        }catch (Exception e){
            logger.error("Exception in getNextOrder", e);
            throw new InternalServerErrorException("Internal error");
        }
        if(idOrdine != -1){
            try {
                response = infoOrdineConnector.getInfoOrdine(idOrdine);
            }catch (Exception e ){
                logger.error("Exception in infoOrderConnector", e);
                throw new InternalServerErrorException();
            }
        }

        return response;
    }

    public void completaOrdine(long idOrdine) {
        try{
            modificaStatoOrdineConnector.modificaOrdine(idOrdine, StatoEnum.COMPLETED);
        }catch (Exception e){
            logger.error("Exception in completaOrdine");
            throw new InternalServerErrorException();
        }
    }

    public void cancellaOrdine(long idOrdine) {
        try{
            modificaStatoOrdineConnector.modificaOrdine(idOrdine, StatoEnum.CANCELED);
        }catch (Exception e){
            logger.error("Exception in cancellaOrdine", e);
            throw new InternalServerErrorException();
        }
    }

    public List<InfoOrdineModel> getInfoOrdine(Long idOrdine) {
        List<InfoOrdineModel> responseList;
        try{
            responseList = infoOrdineConnector.getInfoOrdine(idOrdine);
        }catch (Exception e){
            logger.error("Errore nel chiamare getInfoOrdine");
            throw new InternalServerErrorException("Internal Error");
        }
        return responseList;
    }
}
