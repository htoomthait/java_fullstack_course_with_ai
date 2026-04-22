package net.htoomaungthait.buynowdotcom.common.exception.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityExistsException extends RuntimeException{

    private String statusCode;

    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(String message, String statusCode) {
        super(message);

        this.setStatusCode(statusCode);
    }
}
