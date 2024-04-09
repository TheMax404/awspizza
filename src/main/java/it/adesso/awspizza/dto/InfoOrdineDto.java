package it.adesso.awspizza.dto;

import it.adesso.awspizza.model.StatoEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoOrdineDto {
    private Long idOrdine;
    private Double totale;
    private String note;
    private StatoEnum stato;
    private LocalDateTime dataInserimento;
    private LocalDateTime dataCompletamento;
    private List<PiattoDto> piatti;
}
