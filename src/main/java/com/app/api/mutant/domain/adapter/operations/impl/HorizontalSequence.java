package com.app.api.mutant.domain.adapter.operations.impl;

import com.app.api.mutant.domain.adapter.operations.DnaOperations;
import com.app.api.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Clase que permite validar el DNA de una persona de forma horizontal.
 */
public class HorizontalSequence implements DnaOperations {

    private static final Logger log = LoggerFactory.getLogger(HorizontalSequence.class);

    @Override
    public boolean validIsMutant(List<char[]> listOfSequenceDna) {
        boolean personIsMutant = Boolean.FALSE;

        log.info("[Horizontal-Sequence] : Start");

        for (char[] eachSequenceOfDna : listOfSequenceDna) {

            if (personIsMutant) {
                break;
            }

            //contador que al tener un valor igual o mayor a 3, valida que una persona es mutante
            int cont = Utility.PRIMITIVE_INT_ZERO;

            for (int j = Utility.PRIMITIVE_INT_ZERO; j < eachSequenceOfDna.length - Utility.PRIMITIVE_INT_ONE; j++) {

                //asignar primera y segunda letra de la secuencia de DNA
                char firstLetter = eachSequenceOfDna[j];
                char secondLetter = eachSequenceOfDna[j + Utility.PRIMITIVE_INT_ONE];

                //cuando las 2 letras a comparar son iguales, se incrementa el contador
                //En caso contrario, se reinicia
                cont = (firstLetter == secondLetter) ? cont + Utility.PRIMITIVE_INT_ONE : Utility.PRIMITIVE_INT_ZERO;

                if (cont >= Utility.PRIMITIVE_INT_THREE) {
                    personIsMutant = Boolean.TRUE;
                    break;
                }
            }
        }
        log.info("[Horizontal-Sequence] : Result of Validation = {}", personIsMutant);
        return personIsMutant;
    }
}
