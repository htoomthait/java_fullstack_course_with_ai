package net.htoomaungthait.buynowdotcom.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusCodeAndMessage {
    private String statusCode;
    private String message;

    public static StatusCodeAndMessage of(String statusCode, String message) {
        return StatusCodeAndMessage.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }


}
