package it.adesso.awspizza.controller;

import it.adesso.awspizza.converter.Converter;
import it.adesso.awspizza.dto.*;
import it.adesso.awspizza.model.CreaOrdineModel;
import it.adesso.awspizza.model.InfoOrdineModel;
import it.adesso.awspizza.model.PiattoModel;
import it.adesso.awspizza.model.StatoOrdineModel;
import it.adesso.awspizza.service.ClienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    private final Converter converter;

    @Autowired
    public ClienteController(ClienteService clienteService, Converter converter) {
        this.clienteService = clienteService;
        this.converter = converter;
    }


    Logger logger = LogManager.getLogger(ClienteController.class);

    @GetMapping("/menu")
    public ResponseEntity<MenuResponseDto> getMenu(@RequestParam(value = "filtro",required = false) String filtro) {
        logger.debug("start /menu");
        MenuResponseDto response = new MenuResponseDto();
        List<PiattoModel> piattoModelList = new ArrayList<>();
        try {
            piattoModelList = clienteService.getMenu(filtro);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        response.setMenu(converter.convertPiattoModeltoPiattoDto(piattoModelList));
        logger.debug("end /menu");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stato/{idOrdine}")
    public ResponseEntity<StatoOrdineResponseDto> getStatoOrdine(@PathVariable("idOrdine") Long idOrdine) {
        logger.debug("start /stato with idOrdine: {}", idOrdine);
        StatoOrdineResponseDto response;
        StatoOrdineModel statoOrdineModel = clienteService.getStatoOrdine(idOrdine);
        response = converter.convertStatoOrdineModelStatoOrdineDto(statoOrdineModel);
        logger.debug("end /stato with idOrdine: {}", idOrdine);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/info/{idOrdine}")
    public ResponseEntity<InfoOrdineDto> getInfoOrdine(@PathVariable("idOrdine") Long idOrdine) {
        logger.debug("start /info with idOrdine: {}", idOrdine);
        InfoOrdineDto response;
        List<InfoOrdineModel> infoOrdineModelList = clienteService.getInfoOrdine(idOrdine);
        response = converter.convertInforOrdineModelInfoOrdineDto(infoOrdineModelList);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/crea")
    public ResponseEntity<CreaOrdineResponseDto> creaOrdine(@RequestBody CreaOrdineRequestDto dto) {
        CreaOrdineModel request = converter.convertCreaOrdineDtoCreaOrdineRequestModel(dto);
        long idOrdine = clienteService.creaOrdine(request);
        CreaOrdineResponseDto responseDto = new CreaOrdineResponseDto(idOrdine);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @DeleteMapping("/cancella/{idOrdine}")
    public ResponseEntity<Void> cancellaOrdine(@PathVariable("idOrdine") Long idOrdine){
        clienteService.cancellaOrdine(idOrdine);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
