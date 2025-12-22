package info.htoomaungthait.crash.course.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FmtErrorRes {
    private String statusCode;

    private String statusText;

    private String errorMessage;


    public static FmtErrorRes of(String statusCode, String statusText, String errorMessage) {
        return FmtErrorRes.builder()
                .statusCode(statusCode)
                .statusText(statusText)
                .errorMessage(errorMessage)
                .build();
    }
}
