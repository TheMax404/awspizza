package it.adesso.awspizza.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoOrdineModel {
    private Long idOrdine;
    private String stato;
    private Double totale;
    private Date dataInserimento;
    private Date dataCompletamento;
    private Long idPiatto;
    private String nome;
    private String note;
    private Double prezzo;
}
