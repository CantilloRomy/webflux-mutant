package com.app.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Object> handleBusinessErrorException(
            BusinessException ex) {
        return Mono.just(ex.getMessage());
    }

    @ExceptionHandler(ItIsNotMutantException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Mono<Object> handleIsNotMutantException(
            ItIsNotMutantException ex) {
        return Mono.just(ex.getMessage());
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Object> handleNoContentException(
            NoContentException ex) {
        return Mono.just(ex.getMessage());
    }
}
