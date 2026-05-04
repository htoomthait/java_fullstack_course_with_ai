package net.htoomaungthait.buynowdotcom.common.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityExistsException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.GeneralException;
import net.htoomaungthait.buynowdotcom.common.response.ErrorResponse;
import net.htoomaungthait.buynowdotcom.common.response.MultiErrorResponse;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodesAndMessages;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status("Internal Server Error")
                .statusCode(ex.getStatusCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        // Log the real error internally (VERY IMPORTANT)
        log.error("General exception occurred: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }


   @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalGeneralException(Exception ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status("General Error")
                .statusCode("GEN_001")
                .message("Something went wrong. Please contact support.")
                .timestamp(LocalDateTime.now())
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
                StatusCodesAndMessages.getByStatusCode("CER_005").getMessage(),
                LocalDateTime.now()
        );

        log.info("Type Mismatch: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {


        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "No error message"
                ));


        MultiErrorResponse errorResponse = MultiErrorResponse.of(
                "CER_004",
                "Validation Failed",
                StatusCodesAndMessages.getByStatusCode("CER_004").getMessage(),
                fieldErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<MultiErrorResponse> handleHandlerMethodValidation(
            HandlerMethodValidationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getParameterValidationResults().forEach(result -> {

            String field = result.getMethodParameter().getParameterName();

            result.getResolvableErrors().forEach(error -> {
                String message = error.getDefaultMessage() != null
                        ? error.getDefaultMessage()
                        : "Validation error";

                errors.put(field, message);
            });
        });

        MultiErrorResponse errorResponse = MultiErrorResponse.of(
                "CER_004",
                "Validation Failed",
                "Invalid request parameters",
                errors
        );

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
                StatusCodesAndMessages.getByStatusCode("CER_004").getMessage()  +message,
                LocalDateTime.now()
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
                StatusCodesAndMessages.getByStatusCode("CER_001").getMessage(),
                LocalDateTime.now()
        );

        log.info("Method Not Allowed: {}", ex.getMessage());


        return ResponseEntity.status(405).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ex.getStatusCode(),
                "Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ex.getStatusCode(), "Data Already Exists",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(409).body(errorResponse);
    }

}
