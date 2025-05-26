package xg.demo.test.pmbt_Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xg.demo.test.pojotest.Animal;

import java.lang.reflect.Constructor;

@RestController
@RequestMapping("/test2")
@Slf4j
public class test2 {

    @RequestMapping("/test1")
    public String test1()throws Exception{
        log.info("test1,api执行了！");

        Class<?> aClass = Class.forName("xg.demo.test.pojotest.cat");
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
        Animal o = (Animal) declaredConstructor.newInstance();
        o.eat();
        return "ok";
    }
}
