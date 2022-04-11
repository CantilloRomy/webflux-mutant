package com.app.api.mutant.domain.adapter.operations;

import java.util.List;

public interface DnaOperations {
    boolean validIsMutant(List<char[]> listOfSequenceDna);
}
