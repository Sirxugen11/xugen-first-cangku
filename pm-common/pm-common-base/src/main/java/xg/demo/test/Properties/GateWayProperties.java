package xg.demo.test.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("pm.gateway.config")
@RefreshScope
@Data
public class GateWayProperties {
    //作者
    private String actor;
    //白名单
    private List<String> whitelist;

}
