package com.todojira.ServiceTask.Exceptions.GlobalHandlerException;

import com.todojira.ServiceTask.Exceptions.ObjectValidationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectValidationException.class)
    public ResponseEntity<RepresentationException> handleObjectValidationException(ObjectValidationException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error(e.getMessage())
                .source(e.getCause().getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(400))
                .body(representationException);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RepresentationException> handleEntityNotFoundException(EntityNotFoundException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error(e.getMessage())
                .source(e.getCause().getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(400))
                .body(representationException);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<RepresentationException> handleEntityExistsException(EntityExistsException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error(e.getMessage())
                .source(e.getCause().getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(400))
                .body(representationException);
    }

}
