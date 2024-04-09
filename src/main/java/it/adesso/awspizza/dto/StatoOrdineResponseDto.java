package it.adesso.awspizza.dto;

import it.adesso.awspizza.model.StatoEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatoOrdineResponseDto {
    private Long id;
    private StatoEnum stato;
    private LocalDateTime dataInserimento;
    private LocalDateTime dataCompletamento;
}
