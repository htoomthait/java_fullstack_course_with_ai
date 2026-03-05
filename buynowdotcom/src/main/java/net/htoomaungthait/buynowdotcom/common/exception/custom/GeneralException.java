package net.htoomaungthait.buynowdotcom.common.exception.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralException extends RuntimeException{

    private String statusCode;

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, String statusCode) {
        super(message);

        this.setStatusCode(statusCode);
    }
}
