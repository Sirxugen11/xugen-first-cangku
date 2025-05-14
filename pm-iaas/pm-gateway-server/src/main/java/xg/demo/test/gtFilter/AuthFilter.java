package xg.demo.test.gtFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xg.demo.test.Properties.GateWayProperties;

import java.util.Date;
import java.util.HashMap;

/**
 全局token过滤器* 前后端约定好令牌存放的位置:请求头的Authorization。
 token格式是：bearer token（bearer + 空格 + token）

 **/

//@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private GateWayProperties gateWayProperties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token;
        //获取请求路径
        String path = request.getPath().toString();
        //log.info(path);
        //判断请求是不是在白名单之内
        if(gateWayProperties.getWhitelist().contains(path)){
            log.info("白名单请求，时间：{},请求的Api路径：{}",new Date(),path);
            //如果在就直接放行请求
            return chain.filter(exchange);
        }

        //从约定好的位置-请求头的Authorization获取值（值的格式为：bearer token）
        String authorizationValue = request.getHeaders().getFirst("Authorization");
        //判断authorizationValue是否有值
        if (StringUtils.hasText(authorizationValue)) {
            //去除bearer ，获取纯token
            token = authorizationValue.replaceFirst("bearer ", "");
        }else {
            //设置响应体的格式
            response.getHeaders().set("content-type","application/json;charset=utf-8");
            //创建map，存放自定义信息
            HashMap<String, Object> responsemessage = new HashMap<>();
            responsemessage.put("code",10000);
            responsemessage.put("message","请求中没有authorization的值！");
            //创建一个转换器对象
            ObjectMapper mapper = new ObjectMapper();
            //把map转换成一个字节数组
            byte[] bytes = new byte[0];  //定义一个长度为0的byte数组，他会根据实际大小自动扩容
            try {
                bytes = mapper.writeValueAsBytes(responsemessage);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ////通过bufferFactory()方法把字节数组转换成一个数据包
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            ////
            Mono<Void> mono = response.writeWith(Mono.just(wrap));
            return mono;
        }

        //判断token是否有值和redis里面的Token是否过期（这里规定往redis里存token的key为Token）
        if(StringUtils.hasText(token) && stringRedisTemplate.hasKey("Token")){
            //判断token权限的代码

            return chain.filter(exchange);
        }else {
            //设置响应体的格式
            response.getHeaders().set("content-type","application/json;charset=utf-8");
            //创建map，存放自定义信息
            HashMap<String, Object> responsemessage = new HashMap<>();
            responsemessage.put("code",10086);
            responsemessage.put("message","请求中没有token的值或者token过期！");
            //创建一个转换器对象
            ObjectMapper mapper = new ObjectMapper();
            //把map转换成一个字节数组
            byte[] bytes = new byte[0];  //定义一个长度为0的byte数组，他会根据实际大小自动扩容
            try {
                bytes = mapper.writeValueAsBytes(responsemessage);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //通过bufferFactory()方法把字节数组转换成一个数据包
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            //
            Mono<Void> mono = response.writeWith(Mono.just(wrap));
            return mono;
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
