package xg.demo.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("xg.demo.test.Dao")
//@EnableCaching  //开启redis注解版缓存
@EnableFeignClients
public class pmauthserverapplication {
    public static void main(String[] args) {
        SpringApplication.run(pmauthserverapplication.class,args);
    }
}
