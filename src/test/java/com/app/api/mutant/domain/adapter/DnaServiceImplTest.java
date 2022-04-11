package com.app.api.mutant.domain.adapter;

import com.app.api.exceptions.BusinessException;
import com.app.api.exceptions.ItIsNotMutantException;
import com.app.api.mutant.application.DnaService;
import com.app.api.mutant.common.DnaFactory;
import com.app.api.mutant.domain.dto.DnaDto;
import com.app.api.mutant.domain.dto.DnaResume;
import com.app.api.mutant.domain.entity.Dna;
import com.app.api.mutant.domain.repo.DnaRepository;
import com.app.api.utils.Utility;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class DnaServiceImplTest {

    @MockBean
    DnaRepository dnaRepository;

    @Autowired
    DnaService dnaService;

    @Test
    void When_FindMutantWithHorizontalSequence_Expect_SavedRecord() {

        //given
        Mono<DnaDto> fakeDnaDtoMonoWithHorizontalSequence = Mono.just(new DnaDto(Arrays.asList("ATAGGT", "CTGGAC", "TTAAAA", "AGGAGG", "CTCCGA", "TCACTG")));
        when(dnaRepository.save(Mockito.any())).thenReturn(Mono.just(DnaFactory.aDna().isMutant(Boolean.TRUE).build()));

        //when
        Mono<Void> result = dnaService.findMutant(fakeDnaDtoMonoWithHorizontalSequence);

        //then
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void When_FindMutantWithVerticalSequence_Expect_SavedRecord() {

        //given
        Mono<DnaDto> fakeDnaDtoMonoWithVerticalSequence = Mono.just(new DnaDto(Arrays.asList("ATAGGT", "CTGGAC", "TTAGAA", "AGAGAG", "CTCCGA", "TCACTG")));
        when(dnaRepository.save(Mockito.any())).thenReturn(Mono.just(DnaFactory.aDna().isMutant(Boolean.TRUE).build()));

        //when
        Mono<Void> result = dnaService.findMutant(fakeDnaDtoMonoWithVerticalSequence);

        //then
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void When_FindMutantWithObliqueSequence_Expect_SavedRecord() {

        //given
        Mono<DnaDto> fakeDnaDtoMonoWithObliqueSequence = Mono.just(new DnaDto(Arrays.asList("ATAGGT", "CAGAAC", "TTAGAA", "AGAAAG", "CTCCGA", "TCACTG")));
        when(dnaRepository.save(Mockito.any())).thenReturn(Mono.just(DnaFactory.aDna().isMutant(Boolean.TRUE).build()));

        //when
        Mono<Void> result = dnaService.findMutant(fakeDnaDtoMonoWithObliqueSequence);

        //then
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void Should_ThrowItIsNotMutant_When_FindMutantWithResulItIsNotMutant() {

        //given
        Mono<DnaDto> fakeDnaDtoMonoWithObliqueSequence = Mono.just(new DnaDto(Arrays.asList("ATAGGT", "CAGAAC", "TTTGAA", "AGAAAG", "CTCCGA", "TCACTG")));
        when(dnaRepository.save(Mockito.any())).thenReturn(Mono.just(DnaFactory.aDna().isMutant(Boolean.FALSE).build()));

        //when
        Mono<Void> result = dnaService.findMutant(fakeDnaDtoMonoWithObliqueSequence);

        //then
        StepVerifier.create(result)
                .expectError(ItIsNotMutantException.class)
                .verify();
    }

    @Test
    void Should_ThrowBusinessException_When_ListHaveDifferentLengthOfEachString() {

        //given
        //the first string have 5 characters and the others have 6 characters.
        Mono<DnaDto> fakeDnaDtoMonoBadFormatWithDifferentLengthOfString = Mono.just(new DnaDto(Arrays.asList("ATAGG", "CTGGAC", "TTAAAA", "AGGAGG", "CTCCGA", "TCACTG")));
        //when
        Mono<Void> result = dnaService.findMutant(fakeDnaDtoMonoBadFormatWithDifferentLengthOfString);

        //then
        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    void Should_ThrowBusinessException_When_ListHaveDifferentCharacterPermitted() {

        //given
        //The first string have a H character, the permitted character are A,T,G,C.
        Mono<DnaDto> fakeDnaDtoMonoCorrectFormat = Mono.just(new DnaDto(Arrays.asList("ATAGGH", "CTGGAC", "TTAAAA", "AGGAGG", "CTCCGA", "TCACTG")));
        //when
        Mono<Void> result = dnaService.findMutant(fakeDnaDtoMonoCorrectFormat);
        //then
        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    void When_GetResume_Expect_DnaResume() {
        //give
        List<Dna> list = new ArrayList<>();
        list.add(DnaFactory.aDna().isMutant(false).build());
        list.add(DnaFactory.aDna().id(Utility.LONG_FOUR).isMutant(false).build());
        list.add(DnaFactory.aDna().id(Utility.LONG_THREE).isMutant(false).build());
        list.add(DnaFactory.aDna().id(Utility.LONG_FOUR).isMutant(false).build());

        Flux<Dna> dnaFlux = Flux.fromIterable(list);
        when(dnaRepository.findAll()).thenReturn(dnaFlux);

        //when
        Mono<DnaResume> result = dnaService.getResume();

        //then
        StepVerifier.create(result)
                .expectNextMatches(dnaResume -> Integer.valueOf(4).equals(dnaResume.getCountHumanDna()))
                .expectComplete()
                .verify();

    }

}