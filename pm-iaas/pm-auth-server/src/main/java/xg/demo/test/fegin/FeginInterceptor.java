package xg.demo.test.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * feign拦截器
 *  作用：解决服务之间调用没有token的情况
 *
 *  浏览器 -> A服务 -> B服务
 *  定时器 -> A服务
 */
//@Component
public class FeginInterceptor implements RequestInterceptor {
    @Autowired
    private HttpServletRequest request;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取从浏览器中的token
        String token = request.getHeader("Authorization");

        //A服务调用B服务的时候，也会发起个请求，把来自浏览器的请求中携带的token，也设置给fegin调用产生的请求
        requestTemplate.header("Authorization",token);

    }
}
