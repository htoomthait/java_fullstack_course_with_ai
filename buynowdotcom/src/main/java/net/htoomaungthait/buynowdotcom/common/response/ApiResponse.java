package net.htoomaungthait.buynowdotcom.common.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiResponse<T> {

    private String statusCode;

    private String status;

    private String message;

    private T data;


  public static <T> ApiResponse<T> of(String statusCode, String status, String message, T data) {
        return ApiResponse.<T>builder()
                .statusCode(statusCode)
                .status(status)
                .message(message)
                .data(data)
                .build();
    }


}
