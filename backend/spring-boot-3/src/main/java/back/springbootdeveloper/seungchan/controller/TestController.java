package back.springbootdeveloper.seungchan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testController(){
        return "Hello World";
    }
}
