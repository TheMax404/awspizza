package it.adesso.awspizza.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PiattoModel {
    private Long id;
    private String nome;
    private String descrizione;
    private Double prezzo;
}
