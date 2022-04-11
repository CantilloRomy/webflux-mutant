package com.app.api.mutant.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaResume {
    @Schema(example = "40", description = "Total mutant found")
    private Integer countMutantDna;
    @Schema(example = "100", description = "Total people checked")
    private Integer countHumanDna;
    @Schema(example = "0.4", description = "Ratio")
    private Double ratio;

}
