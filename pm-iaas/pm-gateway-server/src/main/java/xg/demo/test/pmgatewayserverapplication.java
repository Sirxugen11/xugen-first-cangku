package xg.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xg.demo.test.Properties.GateWayProperties;

@SpringBootApplication
public class pmgatewayserverapplication {

    public static void main(String[] args) {
        SpringApplication.run(pmgatewayserverapplication.class,args);

    }
}
