package it.adesso.awspizza.model;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatoOrdineModel {
    private Long id;
    private String stato;
    private Date dataInserimento;
    private Date dataCompletamento;
}
