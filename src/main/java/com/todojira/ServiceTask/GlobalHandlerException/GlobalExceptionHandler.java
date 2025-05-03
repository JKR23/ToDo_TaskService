package com.todojira.ServiceTask.GlobalHandlerException;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.todojira.ServiceTask.Exceptions.ObjectValidationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectValidationException.class)
    public ResponseEntity<RepresentationException> handleObjectValidationException(ObjectValidationException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(400))
                .body(representationException);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RepresentationException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error("Status exists")
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
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(400))
                .body(representationException);
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<RepresentationException> handleInvalidDefinitionException(InvalidDefinitionException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error("error deserialization of LocalDateTime by jackson")
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(500))
                .body(representationException);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RepresentationException> handleRuntimeException(RuntimeException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(500))
                .body(representationException);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RepresentationException> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error("format LocalDateTime invalid")
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(500))
                .body(representationException);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RepresentationException> handleIllegalArgumentException(IllegalArgumentException e) {
        RepresentationException representationException = RepresentationException
                .builder()
                .error(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(500))
                .body(representationException);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("File size exceeds the maximum limit.");
    }

}
