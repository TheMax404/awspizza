package it.adesso.awspizza.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreaOrdineRequestDto {
    private String note;
    private List<Integer> piatti;
}
