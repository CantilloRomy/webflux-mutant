package com.app.api.mutant.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DnaDto {

    @Schema(example = "[\"ATAGGT\", \"CTGGAC\", \"TTAAAA\", \"AGGAGG\"]",
            description = "Allow Send Array N*N of string with the same length, 4x4 or 5x5 or 6x6 ")
    private List<String> dna;

}
