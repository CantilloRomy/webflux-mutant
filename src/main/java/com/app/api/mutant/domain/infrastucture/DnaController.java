package com.app.api.mutant.domain.infrastucture;

import com.app.api.exceptions.BusinessException;
import com.app.api.mutant.application.DnaService;
import com.app.api.mutant.domain.dto.DnaDto;
import com.app.api.mutant.domain.dto.DnaResume;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/dna")
public class DnaController {

    private static final Logger log = LoggerFactory.getLogger(DnaController.class);
    private final DnaService dnaService;

    public DnaController(DnaService dnaService) {
        this.dnaService = dnaService;
    }

    @Operation(description = "Eval if a person is mutant by sequence of DNA", responses = {
            @ApiResponse(responseCode = "200", description = "It is Mutant"),
            @ApiResponse(responseCode = "403", description = "It is not Mutant"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/mutant")
    public Mono<Void> findMutant(@RequestBody DnaDto dnaDtoMono) {
        log.info("[Find-Mutant-Controller] : Start");

        if(!Optional.ofNullable(dnaDtoMono.getDna()).isPresent()){
            return Mono.error(() -> new BusinessException("It is not allow null values"));
        }
        return dnaService.findMutant(Mono.just(dnaDtoMono));
    }

    @Operation(description = "Return a resume from all human dna checked", responses = {
            @ApiResponse(responseCode = "200", description = "Result of Resume"),
            @ApiResponse(responseCode = "204", description = "There is not record")
    })
    @GetMapping("/stats")
    public Mono<DnaResume> getResume(){
        log.info("[Get-Resume-Controller] : Start");
        return dnaService.getResume();
    }
}
