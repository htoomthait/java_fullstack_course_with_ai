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
public class ApiResponse {

    private String message;

    private Object data;


    public static ApiResponse of(String message, Object data) {
        return ApiResponse.builder()
                .message(message)
                .data(data)
                .build();
    }


}
