package com.app.api.mutant.domain.adapter.operations.impl;

import com.app.api.mutant.domain.adapter.operations.DnaOperations;
import com.app.api.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VerticalSequence implements DnaOperations {

    private static final Logger log = LoggerFactory.getLogger(VerticalSequence.class);

    @Override
    public boolean validIsMutant(List<char[]> listOfSequenceDna) {
        boolean personIsMutant = Boolean.FALSE;

        log.info("[Vertical-Sequence] : Start");

        int lengthOfFirstSequence = listOfSequenceDna.get(Utility.PRIMITIVE_INT_ZERO).length;

        for (int i = Utility.PRIMITIVE_INT_ZERO; i < lengthOfFirstSequence; i++) {

            if (personIsMutant) {
                break;
            }

            personIsMutant = iterateVerticalForm(listOfSequenceDna, i);
        }

        log.info("[Vertical-Sequence] : Result of Validation = {}", personIsMutant);
        return personIsMutant;
    }

    private Boolean iterateVerticalForm(List<char[]> listOfSequenceDna, int index) {
        char firstLetter = (char) Utility.PRIMITIVE_INT_ZERO;
        char secondLetter;

        //contador que al tener un valor igual o mayor a 3, valida que una persona es mutante
        int cont = Utility.PRIMITIVE_INT_ZERO;

        boolean fistIteration = Boolean.TRUE;

        for (char[] eachSequenceOfDna : listOfSequenceDna) {

            //validar si es la primer vez que se itera, asignar la primera letra de la secuencia de DNA
            if (fistIteration) {
                firstLetter = eachSequenceOfDna[index];
                fistIteration = Boolean.FALSE;
                continue;
            }

            secondLetter = eachSequenceOfDna[index];

            //cuando las 2 letras a comparar son iguales se incrementa el contador
            //En caso contrario, se reasigna la variable firstLetter, y se reinicia el contador
            if (firstLetter == secondLetter) {
                cont++;
            } else {
                firstLetter = eachSequenceOfDna[index];
                cont = Utility.PRIMITIVE_INT_ZERO;
            }

            if (cont >= Utility.PRIMITIVE_INT_THREE) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
