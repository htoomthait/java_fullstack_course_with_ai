package info.htoomaungthait.crash.course.home.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public String home() {
        return "Welcome to Crash Course Application!";
    }
}
