package info.htoomaungthait.crash.course.common.controller;

import info.htoomaungthait.crash.course.common.dto.FmtResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    public <T> FmtResponse<T> buildResponse(String statusCode, String statusText, String message, T data) {
        return FmtResponse.of(statusCode, statusText, message, data);
    }
}
