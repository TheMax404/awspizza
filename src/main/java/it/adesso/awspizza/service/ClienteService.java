package it.adesso.awspizza.service;

import it.adesso.awspizza.connector.*;
import it.adesso.awspizza.exception.BadRequestErrorException;
import it.adesso.awspizza.exception.InternalServerErrorException;
import it.adesso.awspizza.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final MenuConnector menuConnector;
    private final StatoOrdineConnector statoOrdineConnector;
    private final InfoOrdineConnector infoOrdineConnector;
    private final CreaOrdineConnector creaOrdineConnector;
    private final CancellaOrdineConnector cancellaOrdineConnector;

    @Autowired
    public ClienteService(MenuConnector menuConnector,
                          StatoOrdineConnector statoOrdineConnector,
                          InfoOrdineConnector infoOrdineConnector,
                          CreaOrdineConnector creaOrdineConnector,
                          CancellaOrdineConnector cancellaOrdineConnector) {
        this.menuConnector = menuConnector;
        this.statoOrdineConnector = statoOrdineConnector;
        this.infoOrdineConnector = infoOrdineConnector;
        this.creaOrdineConnector = creaOrdineConnector;
        this.cancellaOrdineConnector = cancellaOrdineConnector;
    }

    Logger logger = LogManager.getLogger(ClienteService.class);


    public List<PiattoModel> getMenu(String filtro) {
        List<PiattoModel> piattoModelList;
        if (StringUtils.isNotEmpty(filtro)) {
            filtro = "%" + filtro + "%";
        }
        try {
            piattoModelList = menuConnector.call(filtro);
        } catch (Exception e) {
            logger.error("Errore nel chiamare getMenu: ", e);
            throw new InternalServerErrorException("Internal error");
        }
        return piattoModelList;
    }

    public StatoOrdineModel getStatoOrdine(Long idOrdine) {
        StatoOrdineModel response = new StatoOrdineModel();
        OrdineModel ordine;
        try {
            ordine = statoOrdineConnector.getStatoOrdine(idOrdine);
        } catch (Exception e) {
            logger.error("Errore nel chiamare getStatoOrdine");
            throw new InternalServerErrorException("Internal Error");
        }
        if(ordine == null || ordine.getId() == null){
            logger.error("Id ordine non presente");
            throw new BadRequestErrorException("Id passato non presente");
        }
        response.setId(ordine.getId());
        response.setStato(ordine.getStato());
        response.setDataInserimento(ordine.getDataInserimento());
        response.setDataCompletamento(ordine.getDataCompletamento());
        return response;
    }

    public List<InfoOrdineModel> getInfoOrdine(Long idOrdine){
        List<InfoOrdineModel> responseList;
        try{
            responseList = infoOrdineConnector.getInfoOrdine(idOrdine);
        }catch (Exception e){
            logger.error("Errore nel chiamare getInfoOrdine");
            throw new InternalServerErrorException("Internal Error");
        }
        if(responseList == null || responseList.isEmpty()){
            logger.error("Id ordine non presente");
            throw new BadRequestErrorException("Id passato non presente");
        }
        return responseList;
    }

    public int creaOrdine(CreaOrdineModel model) {
        model.setStato(StatoEnum.PENDING.toString());
        int idOrdine = -1;
        try {
            idOrdine = creaOrdineConnector.creaOrdine(model);
        }catch (Exception e){
            logger.error("Errore nel chiamare creaOrdine");
            throw new InternalServerErrorException("Errore nella creazione dell'ordine");
        }
        return idOrdine;
    }

    public void cancellaOrdine(Long idOrdine){
        try{
            cancellaOrdineConnector.modificaOrdine(idOrdine, StatoEnum.CANCELED);
        }catch (Exception e){
            logger.error("Errore nel chiamare cancellaOrdine ", e);
            throw new InternalServerErrorException("Errore nella cancellazione dell'ordine");
        }
    }

}
