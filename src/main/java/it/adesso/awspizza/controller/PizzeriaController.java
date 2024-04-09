package it.adesso.awspizza.controller;

import it.adesso.awspizza.converter.Converter;
import it.adesso.awspizza.dto.InfoOrdineDto;
import it.adesso.awspizza.dto.OrdineListDto;
import it.adesso.awspizza.model.InfoOrdineModel;
import it.adesso.awspizza.model.OrdineModel;
import it.adesso.awspizza.model.StatoEnum;
import it.adesso.awspizza.service.PizzeriaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizzeria")
public class PizzeriaController {


    private final PizzeriaService pizzeriaService;
    private final Converter converter;

    @Autowired
    public PizzeriaController(PizzeriaService pizzeriaService, Converter converter) {
        this.pizzeriaService = pizzeriaService;
        this.converter = converter;
    }

    private final Logger logger = LogManager.getLogger(PizzeriaController.class);
    @GetMapping("/ordine")
    public ResponseEntity<OrdineListDto> getOrdini(@RequestParam(value = "stato", required = false)List<StatoEnum> stato){
        logger.debug("start /ordine");
        OrdineListDto response;
        List<OrdineModel> model = pizzeriaService.getOrdini(stato);
        response = converter.convertOrdineModelOrdineDto(model);
        logger.debug("end /ordine");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/ordine/{idOrdine}")
    public ResponseEntity<InfoOrdineDto> putOrdine(@PathVariable("idOrdine") Long idOrdine){
        logger.debug("start /ordine");
        InfoOrdineDto response;
        List<InfoOrdineModel> model = pizzeriaService.putOrdine(idOrdine);
        response = converter.convertInforOrdineModelInfoOrdineDto(model);
        logger.debug("end /ordine");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/ordine/{idOrdine}")
    public ResponseEntity<InfoOrdineDto> getInfoOrdine(@PathVariable("idOrdine") Long idOrdine){
        logger.debug("start /ordine with idOrdine: {}", idOrdine);
        InfoOrdineDto response;
        List<InfoOrdineModel> infoOrdineModelList = pizzeriaService.getInfoOrdine(idOrdine);
        response = converter.convertInforOrdineModelInfoOrdineDto(infoOrdineModelList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/nextOrder")
    public ResponseEntity<InfoOrdineDto> nextOrdine(){
        logger.debug("start /nextOrder");
        InfoOrdineDto response;
        List<InfoOrdineModel> model = pizzeriaService.getNextOrdine();
        response = converter.convertInforOrdineModelInfoOrdineDto(model);
        logger.debug("end /nextOrder");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/completa/{idOrdine}")
    public ResponseEntity<Void> completaOrdine(@PathVariable("idOrdine") Long idOrdine){
        logger.debug("start /completa con idOrdine: {}", idOrdine);
        pizzeriaService.completaOrdine(idOrdine);
        logger.debug("end /completa");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cancella/{idOrdine}")
    public ResponseEntity<Void> cancellaOrdine(@PathVariable("idOrdine") Long idOrdine){
        logger.debug("start /completa con idOrdine: {}", idOrdine);
        pizzeriaService.cancellaOrdine(idOrdine);
        logger.debug("end /completa");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
