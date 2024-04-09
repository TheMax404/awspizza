package it.adesso.awspizza.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOrdineModel {
    private Long idPiatto;
    private Long idOrdine;
}
