package net.htoomaungthait.buynowdotcom.common.response;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class MultiErrorResponse {

    private String statusCode;
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors; // field errors

    public static MultiErrorResponse of(String statusCode, String status, String message, Map<String, String> errors) {
        return MultiErrorResponse.builder()
                .statusCode(statusCode)
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
}
