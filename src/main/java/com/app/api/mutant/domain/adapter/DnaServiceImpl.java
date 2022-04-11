package com.app.api.mutant.domain.adapter;

import com.app.api.exceptions.BusinessException;
import com.app.api.exceptions.ItIsNotMutantException;
import com.app.api.exceptions.NoContentException;
import com.app.api.mutant.application.DnaService;
import com.app.api.mutant.domain.adapter.operations.DnaOperations;
import com.app.api.mutant.domain.adapter.operations.impl.HorizontalSequence;
import com.app.api.mutant.domain.adapter.operations.impl.ObliqueSequence;
import com.app.api.mutant.domain.adapter.operations.impl.VerticalSequence;
import com.app.api.mutant.domain.dto.DnaDto;
import com.app.api.mutant.domain.dto.DnaResume;
import com.app.api.mutant.domain.entity.Dna;
import com.app.api.mutant.domain.repo.DnaRepository;
import com.app.api.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DnaServiceImpl implements DnaService {

    private static final Logger log = LoggerFactory.getLogger(DnaServiceImpl.class);

    private static final String MESSAGE_FOR_IS_MUTANT_METHOD = "[Is-Mutant] : Finish, Result = {}";

    private final DnaRepository dnaRepository;


    @Override
    public Mono<Void> findMutant(Mono<DnaDto> dnaDto) {

        List<char[]> listOfSequenceDna = new ArrayList<>();

        StringBuilder stringJoin = new StringBuilder();

        log.info("[dna-service] Find-Mutant : Start");

        return dnaDto.flatMap(dnaDto1 -> {
                    stringJoin.append(String.join(",", dnaDto1.getDna()));
                    dnaDto1.getDna().forEach(eachString -> {
                        char[] arrayOfChar = eachString.toCharArray();
                        listOfSequenceDna.add(arrayOfChar);
                    });
                    log.info("[Is-Mutant] : Init - {}", stringJoin);
                    return Mono.just(listOfSequenceDna);
                })
                .flatMap(this::validTypeOfLetter)
                .flatMap(this::validLengthOfEachStringGreaterThanFour)
                .flatMap(this::validIfEachStringHaveTheSameLength)
                .flatMap(chars -> getBooleanMono(chars, stringJoin))
                .doOnError(throwable -> {
                    if (throwable instanceof ItIsNotMutantException) {
                        save(stringJoin, Boolean.FALSE);
                    }
                })
                .doOnSuccess(aBoolean -> save(stringJoin, Boolean.TRUE));
    }

    @Override
    public Mono<DnaResume> getResume() {
        log.info("[dna-service] Get-Resume : Start");
        Flux<Dna> dnaList = dnaRepository.findAll()
                .switchIfEmpty(Mono.error(new NoContentException("There are not record")))
                .doOnError(throwable -> log.error(throwable.getMessage()));

        Mono<Long> totalHumanSaved = dnaList.count();
        Mono<Long> totalMutantSaved = dnaList.filter(Dna::getMutant).count();

        return Mono.zip(totalHumanSaved, totalMutantSaved)
                .flatMap(tuples ->
                        Mono.just(new DnaResume(
                                tuples.getT2().intValue(),
                                tuples.getT1().intValue(),
                                (double) tuples.getT2() / tuples.getT1()))
                );
    }

    @NotNull
    private Mono<Void> getBooleanMono(List<char[]> listOfSequenceDna, StringBuilder stringJoin) {
        DnaOperations horizontalSequence = new HorizontalSequence();
        if (horizontalSequence.validIsMutant(listOfSequenceDna)) {
            log.info(MESSAGE_FOR_IS_MUTANT_METHOD, Boolean.TRUE);
            return Mono.empty();
        }

        DnaOperations verticalSequence = new VerticalSequence();
        if (verticalSequence.validIsMutant(listOfSequenceDna)) {
            log.info(MESSAGE_FOR_IS_MUTANT_METHOD, Boolean.TRUE);
            return Mono.empty();
        }

        DnaOperations obliqueSequence = new ObliqueSequence();
        if (obliqueSequence.validIsMutant(listOfSequenceDna)) {
            log.info(MESSAGE_FOR_IS_MUTANT_METHOD, Boolean.TRUE);
            return Mono.empty();
        }

        log.info(MESSAGE_FOR_IS_MUTANT_METHOD, Boolean.FALSE);
        throw new ItIsNotMutantException("Is not Mutant");
    }


    private Mono<List<char[]>> validTypeOfLetter(List<char[]> listOfSequenceDna) {
        listOfSequenceDna.forEach(eachVector -> {
            for (char c : eachVector) {
                if (c != 'A' && c != 'C' && c != 'T' && c != 'G') {
                    log.error("[dna-service] Find-Mutant : Character different of permitted : {} ", c);
                    throw new BusinessException("Character different of permitted " + c);
                }
            }
        });
        return Mono.just(listOfSequenceDna);
    }

    private Mono<List<char[]>> validIfEachStringHaveTheSameLength(List<char[]> listOfSequenceDna) {
        int sumOfVectorLength = listOfSequenceDna.stream().mapToInt(eachVector -> eachVector.length).sum();

        if (sumOfVectorLength % listOfSequenceDna.size() != Utility.PRIMITIVE_INT_ZERO) {
            log.error("[dna-service] Find-Mutant : Length of each sequence is different ");
            throw new BusinessException("Length of each sequence is different");
        }
        return Mono.just(listOfSequenceDna);
    }

    private Mono<List<char[]>> validLengthOfEachStringGreaterThanFour(List<char[]> listOfSequenceDna) {
        listOfSequenceDna.forEach(eachVector -> {
            if (eachVector.length < Utility.PRIMITIVE_INT_FOUR) {
                log.error("[dna-service] Find-Mutant : Length of sequence less than four");
                throw new BusinessException("Length of sequence less than four");
            }
        });
        return Mono.just(listOfSequenceDna);
    }

    private void save(StringBuilder dnaSequence, Boolean isMutant) {
        log.info("[dna-service] Save-Mutant : Start");

        Dna dnaEntity = new Dna(dnaSequence.toString(), isMutant);

        dnaRepository.save(dnaEntity)
                .doOnNext(dna -> log.info("[dna-service] Save-Mutant : entity saved - {}", dna))
                .subscribe(dna -> log.info("[dna-service] Save-Mutant : Finish"));
    }

}
