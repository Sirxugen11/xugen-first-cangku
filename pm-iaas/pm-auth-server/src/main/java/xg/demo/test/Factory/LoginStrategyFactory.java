package xg.demo.test.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xg.demo.test.Strategy.LoginStrategy;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginStrategyFactory {

    //pcLoginStrategy和wcLoginStrategy都实现了LoginStrategy接口，且都放到了容器里，所以可以用map集合来接收
    //key是名字，value是对应的实现类
    @Autowired
    public Map<String, LoginStrategy> loginStrategyMap = new HashMap<>();

    public LoginStrategy GetInstance(String loginType){
        System.out.println("loginStrategyMap的大小是： " + loginStrategyMap.size());
        return loginStrategyMap.get(loginType);
    }
}
