package com.app.api.mutant.application;

import com.app.api.mutant.domain.dto.DnaDto;
import com.app.api.mutant.domain.dto.DnaResume;
import reactor.core.publisher.Mono;

public interface DnaService {

    Mono<Void> findMutant(Mono<DnaDto> dnaDto);

    Mono<DnaResume> getResume();
}
