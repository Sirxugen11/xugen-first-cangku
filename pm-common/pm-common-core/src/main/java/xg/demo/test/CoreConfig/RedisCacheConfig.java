package xg.demo.test.CoreConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * redis缓存配置类
 */
//@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        // 创建redis缓存配置类
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // 进行redis配置
        redisCacheConfiguration
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json())) // 设置redis值的序列化方式为json格式,如果不序列化，那么存在redis里面的数据就是一串字符串，虽然影响直观感受，但是不影响使用
                .entryTtl(Duration.ofDays(7))   // 统一设置redis中值的默认过期时间（7天）
                .disableCachingNullValues();    // redis的value值禁止使用空值


        return redisCacheConfiguration;
    }
}
