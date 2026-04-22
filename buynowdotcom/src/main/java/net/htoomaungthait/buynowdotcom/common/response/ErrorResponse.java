package net.htoomaungthait.buynowdotcom.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {

    private String statusCode;

    private String status;

    private LocalDateTime timestamp;

    private String message;

    public static ErrorResponse of(String statusCode, String status, String message, LocalDateTime timestamp) {
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .status(status)
                .message(message)
                .timestamp(timestamp)
                .build();
    }
}
