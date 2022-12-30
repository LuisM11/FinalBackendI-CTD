package com.dh.marin.exceptions;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptions {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptions.class);

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> ResourceNotFoundExceptionHandler (ResourceNotFoundException rnfe){
            LOGGER.error(rnfe);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
    }
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> BadRequestExceptionHandler(BadRequestException badRequestException){
        LOGGER.error(badRequestException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestException.getMessage());
    }
    @ExceptionHandler({DuplicateConflictException.class})
    public ResponseEntity<String> DuplicateConflictExceptionHandler (DuplicateConflictException duplicateConflictException){
        LOGGER.error(duplicateConflictException);
        return new ResponseEntity<>(duplicateConflictException.getMessage(),HttpStatus.CONFLICT);
    }
}
