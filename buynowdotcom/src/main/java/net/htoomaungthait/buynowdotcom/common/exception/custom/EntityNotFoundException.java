package net.htoomaungthait.buynowdotcom.common.exception.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityNotFoundException extends RuntimeException {

    private String statusCode;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, String statusCode) {
        super(message);

        this.setStatusCode(statusCode);
    }



}
