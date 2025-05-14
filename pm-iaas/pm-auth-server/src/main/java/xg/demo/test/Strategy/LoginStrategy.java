package xg.demo.test.Strategy;

import org.springframework.security.core.userdetails.UserDetails;

/*登录策略接口*/
/*以后可能会有pc端的系统登录策略，微信端的，支付宝端的。这里运用策略设计模式，让他们都实现同一个接口，然后就可以写不同的实现类去实现具体功能*/
/*比如现在有serviceA,serviceB,以后会有C,D...等。我们就可以让这些service实现同一个接口，给他们放到spring容器内，然后弄一个工厂，通过约定好的标识
比如1标识就生产serviceA，2标识就生产serviceB，这种设计模式就解决了对修改不支持对新增支持的设计理念。如果使用elseif来判断，以后新增C,D等还要修改
elseif*/
public interface LoginStrategy {

    /*真正处理登录的方法*/
    UserDetails realLogin(String username);
}
