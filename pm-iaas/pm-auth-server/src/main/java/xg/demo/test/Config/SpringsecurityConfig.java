package xg.demo.test.Config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import xg.demo.test.MyUserDetailsServiceImp.PmUserDetailsService;
import xg.demo.test.Untils.JwtUntil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Configuration
public class SpringsecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PmUserDetailsService pmUserDetailsService;
    @Autowired
    private JwtUntil jwtUntil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //加入加密编码器
    @Bean
    @Primary
    public BCryptPasswordEncoder GetBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不允许前端表单登录
        //http.formLogin().disable();

        //关闭跨域请求
        http.csrf().disable();
        //关闭跨站请求伪造
        http.cors().disable();
        //关闭session使用策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //要求所有请求都需要验证
        //http.authorizeRequests().anyRequest().authenticated();

        // 配置登录信息
        http.formLogin()
                .loginProcessingUrl("/test1")// 设置登录URL(设置了之后登录这个url就要在前端的表单登录)
                .successHandler(authenticationSuccessHandler());   // 设置登录成功处理器
                //.failureHandler(authenticationFailureHandler());  // 调协登录失败处理器





    }

    //自定义security安全框架的认证流程
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pmUserDetailsService);
    }

    //登录成功处理器
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        AuthenticationSuccessHandler chuliqi= new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                System.out.println("登录成功处理器执行了！");

                //从security框架中获取认证用户对象(LoginSecurityUserr)
                Object principal = authentication.getPrincipal();
                //把用户认证对象转换成json格式的string
                String json = JSON.toJSONString(principal);
                //把用户对象再封装到token中
                String token = jwtUntil.GetToken(json);

                // 设置响应头信息
                /*httpServletResponse.setContentType();
                httpServletResponse.setCharacterEncoding();*/

                //把token数据响应给前端
                PrintWriter out = httpServletResponse.getWriter();
                out.write(token);
                out.flush();
                out.close();
                //把token存到redis中，并设置8小时过期时间
                stringRedisTemplate.opsForValue().set("Token",token,60*60*8, TimeUnit.SECONDS);
            }
        };
        return chuliqi;
    }
}
