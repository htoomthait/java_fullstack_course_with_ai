package info.htoomaungthait.crash.course.demo.controller;

import info.htoomaungthait.crash.course.common.controller.BaseController;
import info.htoomaungthait.crash.course.common.dto.FmtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public FmtResponse<String> sayHello() {

       return buildResponse(
            "DEMO_001",
            "OK",
            "Demo hello response",
            "Saying hello from the demo controller!"
        );
    }
}
