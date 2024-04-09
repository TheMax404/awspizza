package it.adesso.awspizza.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PiattoDto {
    private Long idPiatto;
    private String nome;
    private String descrizione;
    private Double prezzo;
}
