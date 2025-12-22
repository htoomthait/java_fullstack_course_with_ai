package info.htoomaungthait.crash.course.common.controller;

import info.htoomaungthait.crash.course.common.dto.FmtErrorRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<FmtErrorRes> handleError(WebRequest request) {

        Map<String, Object> error = errorAttributes.getErrorAttributes(
                request,
                ErrorAttributeOptions.defaults()
        );

        log.info(HttpStatus.valueOf((Integer) error.get("status")).toString());

        return ResponseEntity
                .status(HttpStatus.valueOf((Integer) error.get("status")))
                .body(FmtErrorRes.of(
                        "ERR_" + error.get("status"),
                        (String) error.get("error"),
                        (String) error.get("message")
                ));
    }
}
