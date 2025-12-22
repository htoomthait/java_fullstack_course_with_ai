package info.htoomaungthait.crash.course.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private String statusCode;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
