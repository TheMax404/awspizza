package it.adesso.awspizza.converter;

import it.adesso.awspizza.dto.*;
import it.adesso.awspizza.exception.BadRequestErrorException;
import it.adesso.awspizza.model.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class Converter {

    public List<PiattoDto> convertPiattoModeltoPiattoDto(List<PiattoModel> modelList){
        List<PiattoDto> response = new ArrayList<>();
        for(PiattoModel model : modelList){
            PiattoDto piatto = PiattoDto.builder()
                    .idPiatto(model.getId())
                    .nome(model.getNome())
                    .descrizione(model.getDescrizione())
                    .prezzo(model.getPrezzo())
                    .build();
            response.add(piatto);
        }
        return response;
    }

    public StatoOrdineResponseDto convertStatoOrdineModelStatoOrdineDto(StatoOrdineModel model){
        StatoOrdineResponseDto response = new StatoOrdineResponseDto();
        response.setStato(StatoEnum.valueOf(model.getStato()));
        response.setId(model.getId());
        response.setDataInserimento(model.getDataInserimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        if(model.getDataCompletamento() != null) {
            response.setDataCompletamento(model.getDataCompletamento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        return response;
    }

    public InfoOrdineDto convertInforOrdineModelInfoOrdineDto(List<InfoOrdineModel> modelList) {
        InfoOrdineDto response = new InfoOrdineDto();
        if (!modelList.isEmpty()) {
            InfoOrdineModel a = modelList.get(0);
            response.setIdOrdine(a.getIdOrdine());
            response.setStato(StatoEnum.valueOf(a.getStato()));
            response.setTotale(a.getTotale());
            response.setNote(a.getNote());
            response.setDataInserimento(a.getDataInserimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if (a.getDataCompletamento() != null) {
                response.setDataCompletamento(a.getDataCompletamento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        }
        List<PiattoDto> piatti = new ArrayList<>();
        for(InfoOrdineModel model : modelList){
            PiattoDto dto = PiattoDto.builder()
                    .prezzo(model.getPrezzo())
                    .nome(model.getNome())
                    .idPiatto(model.getIdPiatto())
                    .build();
            piatti.add(dto);
        }
        response.setPiatti(piatti);
        return response;
    }

    public CreaOrdineModel convertCreaOrdineDtoCreaOrdineRequestModel(CreaOrdineRequestDto dto){
        if(dto.getPiatti() == null || dto.getPiatti().isEmpty()){
            throw new BadRequestErrorException("Piatti non valorizzato");
        }
        return CreaOrdineModel.builder()
                .note(dto.getNote())
                .piatti(dto.getPiatti())
                .build();
    }

    public OrdineListDto convertOrdineModelOrdineDto(List<OrdineModel> modelList){
        OrdineListDto response = new OrdineListDto();
        List<OrdineDto> ordini = new ArrayList<>();
        for(OrdineModel model : modelList){
            OrdineDto dto = new OrdineDto();
            dto.setStato(StatoEnum.valueOf(model.getStato()));
            dto.setIdOrdine(model.getId());
            dto.setTotale(model.getTotale());
            dto.setNote(model.getNote());
            dto.setDataInserimento(model.getDataInserimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if(model.getDataCompletamento() != null) {
                dto.setDataCompletamento(model.getDataCompletamento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
            ordini.add(dto);
        }
        response.setOrdini(ordini);
        return response;
    }
}
