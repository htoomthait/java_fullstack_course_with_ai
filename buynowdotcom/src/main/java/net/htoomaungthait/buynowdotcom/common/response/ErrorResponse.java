package net.htoomaungthait.buynowdotcom.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {

    private String statusCode;

    private String status;

    private String message;

    public static ErrorResponse of(String statusCode, String status, String message) {
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .status(status)
                .message(message)
                .build();
    }
}
