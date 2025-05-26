package xg.demo.test.Advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/*日志的增加类*/

@Aspect
@Component
@Slf4j
@Order(1)
public class LogAdvice {

    /*@Value("${xg.demo.test.pointcut}")
    private String value;*/


    @After("execution (* xg.demo.test.Controller.*.*(..))")
    public void log(){
        log.info("模拟日志相关业务代码执行了！");
        //System.out.println(value);
    }
}
