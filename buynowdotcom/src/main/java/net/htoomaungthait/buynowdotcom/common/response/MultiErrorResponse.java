package net.htoomaungthait.buynowdotcom.common.response;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class MultiErrorResponse {

    private int status;
    private String statusCode;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors; // field errors
}
