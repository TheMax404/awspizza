package it.adesso.awspizza.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdineListDto {
    List<OrdineDto> ordini;
}
