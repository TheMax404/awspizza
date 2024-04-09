package it.adesso.awspizza.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreaOrdineModel {
    private List<Integer> piatti;
    private String note;
    private String stato;
}
