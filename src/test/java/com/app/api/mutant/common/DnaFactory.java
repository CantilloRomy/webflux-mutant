package com.app.api.mutant.common;

import com.app.api.mutant.domain.entity.Dna;
import com.app.api.utils.Utility;

public class DnaFactory {

    public static Dna.DnaBuilder aDna(){
        return Dna.builder()
                .id(Utility.LONG_ONE)
                .dnaSequence("ATAGGT,CTGGAC,TTAAAA,AGGAGG,CTCCGA,TCACTG");
    }
}
