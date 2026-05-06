package net.htoomaungthait.buynowdotcom.common.exception.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends RuntimeException{

    private String statusCode;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String statusCode) {
        super(message);

        this.setStatusCode(statusCode);
    }

}
