package com.signalement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    /**
     * Gère la demande de demande personnalisée et construit une réponse apiexception.
     *
     * @param ex
     * @return Réponse contenant les détails de l'apiexception
     */
    @ExceptionHandler({RequestException.class})
    public ResponseEntity<ApiException> handleRequestException(RequestException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), ex.getStatus(), LocalDateTime.now());
        return new ResponseEntity<>(apiException, ex.status);
    }

    /**
     * Gère l'exception EntityNotFoundException et construit une réponse apiexception.
     *
     * @param ex
     * @return Réponse contenant les détails de l'apiexception
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);

    }

    /**
     * Gère l'exception MethodArgumentNotValidException et construit une réponse apiexception.
     *
     * @param ex
     * @return Réponse contenant les détails de l'apiexception
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiException> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
