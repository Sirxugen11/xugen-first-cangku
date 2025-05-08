package xg.demo.test.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Auth_Server {

    @GetMapping("/test1")
    @ResponseBody
    public String test1(){
        return "test1";
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2(){
        return "test2";
    }
}
