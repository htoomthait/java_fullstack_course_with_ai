package info.htoomaungthait.crash.course.common.dto;

import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FmtResponse<T> {

    private String statusCode;

    private String statusText;

    private String message;

    private T data;

    public static <T> FmtResponse<T> of(String statusCode, String statusText, String message, T data) {
        return FmtResponse.<T>builder()
                .statusCode(statusCode)
                .statusText(statusText)
                .message(message)
                .data(data)
                .build();
    }

}
