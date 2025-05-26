package xg.demo.test.Advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OneAdvice {

    @Around(value="execution (* xg.demo.test.pmbt_Controller.*.*(..))")
    public Object test1(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("1111111111111111");
        //目标方法的返回值
        Object proceed = joinPoint.proceed();

        //这里不返回目标方法返回值proceed，而返回其他的
        return "66666";
    }
}
