package xg.demo.test.gtFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import xg.demo.test.Properties.GateWayProperties;
import xg.demo.test.Untils.JwtUntil;

import java.util.HashMap;

//@Component
public class GatewayFilter implements GlobalFilter, Ordered {
    @Autowired
    private JwtUntil jwtUntil;
    @Autowired
    private GateWayProperties gateWayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(gateWayProperties.getActor());
        ServerHttpRequest request = exchange.getRequest();
        HttpCookie tokencookie = request.getCookies().getFirst("Token");
        //没有token相关的cookie就返回错误码
        if(tokencookie == null){
            //否则不放行，返回错误码
            ////获取响应对象
            ServerHttpResponse response = exchange.getResponse();
            ////设置响应体的格式
            response.getHeaders().set("content-type","application/json;charset=utf-8");
            ////创建map，存放自定义信息
            HashMap<String, Object> responsemessage = new HashMap<>();
            responsemessage.put("code",10000);
            responsemessage.put("message","请求中没有token！");
            ////创建一个转换器对象
            ObjectMapper mapper = new ObjectMapper();
            ////把map转换成一个字节数组
            byte[] bytes = new byte[0];  //定义一个长度为0的byte数组，他会根据实际大小自动扩容
            try {
                bytes = mapper.writeValueAsBytes(responsemessage);
                System.out.println(bytes.length);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ////通过bufferFactory()方法把字节数组转换成一个数据包
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            ////
            Mono<Void> mono = response.writeWith(Mono.just(wrap));
            return mono;
        }

        String token = tokencookie.getValue();
        String flag = jwtUntil.jiexi(token).getClaim("flag").asString();
        //如果token中的flag值为ok，就放行。
        if(flag.equals("ok")){
            return chain.filter(exchange);
        }else {
            //否则不放行，返回错误码
            ////获取响应对象
            ServerHttpResponse response = exchange.getResponse();
            ////设置响应体的格式
            response.getHeaders().set("content-type","application/json;charset=utf-8");
            ////创建map，存放自定义信息
            HashMap<String, Object> responsemessage = new HashMap<>();
            responsemessage.put("code",10086);
            responsemessage.put("message","请求被gateway的过滤器拦截！");
            ////创建一个转换器对象
            ObjectMapper mapper = new ObjectMapper();
            ////把map转换成一个字节数组
            byte[] bytes = new byte[0];  //定义一个长度为0的byte数组，他会根据实际大小自动扩容
            try {
                bytes = mapper.writeValueAsBytes(responsemessage);
                System.out.println(bytes.length);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ////通过bufferFactory()方法把字节数组转换成一个数据包
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            ////
            Mono<Void> mono = response.writeWith(Mono.just(wrap));
            return mono;
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
