package info.htoomaungthait.crash.course.common.exception;

import info.htoomaungthait.crash.course.common.dto.FmtErrorRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<FmtErrorRes> handleNoHandlerFound(NoHandlerFoundException ex) {

        log.info("Handling 404 URI Not Found: {}", ex.getRequestURL());

        FmtErrorRes error = FmtErrorRes.of(
                "RES_404",
                "URI Not Found",
                "No endpoint found for " + ex.getRequestURL()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FmtErrorRes> handleAllExceptions(Exception ex) {

        log.info("Handling general exception SRV_500 Global: {}", ex.getMessage());

        FmtErrorRes error = FmtErrorRes.of(
                "SRV_500",
                "Internal Server Error",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FmtErrorRes> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        FmtErrorRes error = new FmtErrorRes(
                "REQ_400",
                "Validation Failed",
                errorMsg
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<FmtErrorRes> handleResourceNotFound(
            ResourceNotFoundException ex) {

        FmtErrorRes error = FmtErrorRes.of(
                ex.getStatusCode() != null ? ex.getStatusCode() : "RES_404",
                "Resource Not Found",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<FmtErrorRes> handleIllegalArgument(
            IllegalArgumentException ex) {

        FmtErrorRes error = FmtErrorRes.of(
                "ARG_400",
                ex.getMessage(),
                "Invalid input"
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
