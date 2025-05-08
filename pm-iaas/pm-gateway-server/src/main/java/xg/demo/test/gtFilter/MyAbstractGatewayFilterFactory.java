package xg.demo.test.gtFilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//AbstractGatewayFilterFactory
//类名必须是GatewayFilterFactory结尾
//@Component
public class MyAbstractGatewayFilterFactory extends AbstractGatewayFilterFactory implements Ordered {

    @Override
    public int getOrder() {
        return 1;
    }


    @Override
    public GatewayFilter apply(Object config) {
        GatewayFilter gatewayFilter = new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("MyAbstractGatewayFilterFactory开始执行");
                Mono<Void> mono = chain.filter(exchange);
                System.out.println("MyAbstractGatewayFilterFactory结束");
                return mono;
            }
        };
        return gatewayFilter;
    }
}
