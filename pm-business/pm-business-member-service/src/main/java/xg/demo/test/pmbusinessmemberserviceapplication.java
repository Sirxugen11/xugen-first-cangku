package xg.demo.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("xg.demo.test.Dao")
public class pmbusinessmemberserviceapplication {
    public static void main(String[] args) {
        SpringApplication.run(pmbusinessmemberserviceapplication.class,args);
    }
}
