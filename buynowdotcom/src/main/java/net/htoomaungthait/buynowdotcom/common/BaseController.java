package net.htoomaungthait.buynowdotcom.common;


import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodeAndMessage;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodesAndMessages;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected StatusCodeAndMessage statusCodeAndMessage;

    protected int respondStatus;

    protected <T> ResponseEntity<ApiResponse<T>> makeResponse(
            int httpStatus,
            String statusCode,
            String status,
            String message,
            T data
    ) {

        ApiResponse<T> apiResponse = ApiResponse.of(statusCode, status, message, data);

        return ResponseEntity.status(httpStatus).body(apiResponse);

    }

    protected String getStatusMessageByCode(String statusCode) {
        return StatusCodesAndMessages.getByStatusCode(statusCode).getMessage();
    }
}
