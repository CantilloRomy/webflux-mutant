package com.app.api.mutant.domain.adapter.operations.impl;

import com.app.api.mutant.domain.adapter.operations.DnaOperations;
import com.app.api.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ObliqueSequence implements DnaOperations {

    private static final Logger log = LoggerFactory.getLogger(ObliqueSequence.class);

    @Override
    public boolean validIsMutant(List<char[]> listOfSequenceDna) {
        boolean personIsMutant = Boolean.FALSE;
        char firstLetter = (char) Utility.PRIMITIVE_INT_ZERO; //primera letra a comparar
        char secondLetter; //segunda letra a comparar
        int cont = Utility.PRIMITIVE_INT_ZERO; //cont = 3, es mutante
        int index = Utility.PRIMITIVE_INT_ZERO; //indice de listOfSequenceDna

        log.info("[Oblique-Sequence] : Start");

        for (char[] eachLetterOfSequence : listOfSequenceDna) {

            //Si es primer vez de iteracion, se asigna
            //la primera letra de la secuencia de DNA
            if (index == Utility.PRIMITIVE_INT_ZERO) {
                firstLetter = eachLetterOfSequence[index];
            } else {
                secondLetter = eachLetterOfSequence[index];

                //cuando las 2 letras a comparar son iguales, se incrementa el contador
                //En caso contrario, se reasigna la variables firstLetter, y se reinicia el contador
                if (firstLetter == secondLetter) {
                    cont++;
                } else {
                    firstLetter = eachLetterOfSequence[index];
                    cont = Utility.PRIMITIVE_INT_ZERO;
                }
            }

            if (cont >= Utility.PRIMITIVE_INT_THREE) {
                personIsMutant = Boolean.TRUE;
                break;
            }
            index++;
        }

        log.info("[Oblique-Sequence] : Result of Validation = {}", personIsMutant);
        return personIsMutant;
    }
}
