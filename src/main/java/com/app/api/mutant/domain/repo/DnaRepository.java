package com.app.api.mutant.domain.repo;

import com.app.api.mutant.domain.entity.Dna;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DnaRepository extends ReactiveCrudRepository<Dna, Long> {
}
