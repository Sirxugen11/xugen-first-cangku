package xg.demo.test.MyUserDetailsServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xg.demo.test.Constant.AuthConstant;
import xg.demo.test.Factory.LoginStrategyFactory;
import xg.demo.test.Strategy.LoginStrategy;


import javax.servlet.http.HttpServletRequest;

@Service
public class PmUserDetailsService implements UserDetailsService {
    @Autowired
    private LoginStrategyFactory loginStrategyFactory;


    //这里的参数的username是springsercuity前端登录表单输入的username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //System.out.println(username);
        // 获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 从请求头中获取登录类型
        String loginType = request.getHeader(AuthConstant.LOGIN_TYPE);
        // 判断请求来自于哪个系统
        /*if (AuthConstants.SYS_USER_LOGIN.equals(loginType)) {
            // 商城后台管理系统流程
        } else {
            // 商城用户购物系统流程
        }*/
        //loginType = "pcLoginStrategy";  ////////////////////////////////////////////
        if (!StringUtils.hasText(loginType)) {
            throw new InternalAuthenticationServiceException("非法登录，登录类型不匹配");
        }
        // 通过登录策略工厂获取具体的登录策略对象
        /*LoginStrategy instance = loginStrategyFactory.getInstance(loginType);
        return instance.realLogin(username);*/

        //通过logintype来获取对应的登录策略对象
        LoginStrategy loginStrategy = loginStrategyFactory.GetInstance(loginType);
        //System.out.println("loginStrategyFactory工厂的map是： " + loginStrategyFactory.loginStrategyMap);
        //通过登录策略对象获取UserDetails对象
        UserDetails userDetails = loginStrategy.realLogin(username);
        return userDetails;
    }
}
