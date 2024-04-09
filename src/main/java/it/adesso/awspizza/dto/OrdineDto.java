package it.adesso.awspizza.dto;

import it.adesso.awspizza.model.StatoEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdineDto {
    private Long idOrdine;
    private Double totale;
    private String note;
    private StatoEnum stato;
    private LocalDateTime dataInserimento;
    private LocalDateTime dataCompletamento;
}
