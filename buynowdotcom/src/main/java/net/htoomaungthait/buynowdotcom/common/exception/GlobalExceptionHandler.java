package net.htoomaungthait.buynowdotcom.common.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityExistsException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.common.response.ErrorResponse;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodesAndMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;

import java.time.LocalDateTime;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalGeneralException(Exception ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status("General Error")
                .statusCode("GEN_001")
                .message("Something went wrong. Please contact support.")
                .build();

        // Log the real error internally (VERY IMPORTANT)
        log.error("Unhandled exception occurred", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException .class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        ErrorResponse errorResponse = ErrorResponse.of(
                "CER_005",
                "Invalid Parameter",
                StatusCodesAndMessages.getByStatusCode("CER_005").getMessage()
        );

        log.info("Type Mismatch: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {

        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Validation error");

        ErrorResponse errorResponse = ErrorResponse.of(
                "CER_004",
                "Validation Failed",
                StatusCodesAndMessages.getByStatusCode("CER_004").getMessage()  +message
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodNotAllowedException.class})
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
                "CER_001",
                "Not Allowed",
                StatusCodesAndMessages.getByStatusCode("CER_001").getMessage()
        );

        log.info("Method Not Allowed: {}", ex.getMessage());


        return ResponseEntity.status(405).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ex.getStatusCode(), "Not Found",
                ex.getMessage()
        );

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ex.getStatusCode(), "Data Already Exists",
                ex.getMessage()
        );

        return ResponseEntity.status(409).body(errorResponse);
    }

}
