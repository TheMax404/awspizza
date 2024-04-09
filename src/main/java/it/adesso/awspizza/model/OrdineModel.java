package it.adesso.awspizza.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdineModel {
    private Long id;
    private String stato;
    private Timestamp dataInserimento;
    private Timestamp dataCompletamento;
    private String note;
    private Double totale;
}
