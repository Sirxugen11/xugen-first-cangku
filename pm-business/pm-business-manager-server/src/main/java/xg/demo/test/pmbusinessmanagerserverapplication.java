package xg.demo.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("xg.demo.test.Dao")
@EnableCaching
public class pmbusinessmanagerserverapplication {
    public static void main(String[] args) {
        SpringApplication.run(pmbusinessmanagerserverapplication.class,args);
    }


    @Bean
    public BCryptPasswordEncoder GetBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
