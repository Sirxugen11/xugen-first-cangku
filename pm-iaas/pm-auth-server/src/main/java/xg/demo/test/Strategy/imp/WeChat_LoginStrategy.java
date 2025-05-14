package xg.demo.test.Strategy.imp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import xg.demo.test.Strategy.LoginStrategy;

/*微信端的系统登录策略的具体实现*/
@Service("wc_LoginStrategy")
public class WeChat_LoginStrategy implements LoginStrategy {
    @Override
    public UserDetails realLogin(String username) {
        System.out.println("微信端的认证流程开始：");
        return null;
    }
}
