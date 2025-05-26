package xg.demo.test.Config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import xg.demo.test.Constant.ResourceConstants;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.MyUserDetailsServiceImp.PmUserDetailsService;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.Untils.JwtUntil;
import xg.demo.test.authpojo.PmLoginResult;

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


        // 配置登录信息
        http.formLogin()  //开启表单认证
                .loginProcessingUrl("/doLogin")// security前端表单登录成功之后，浏览器就会被重定向到ip+host+/dologin
                .successHandler(authenticationSuccessHandler())   // 设置登录成功处理器
                .failureHandler(authenticationFailureHandler());  // 调协登录失败处理器

        // 配置登出信息
        http.logout()
                .logoutUrl("doLogout")// 设置登出URL
                .logoutSuccessHandler(logoutSuccessHandler());// 设置登出成功处理器

        //要求所有请求都需要验证
        http.authorizeRequests()
                .antMatchers("/test2","/redis/tokentest1","/redis/tokentest2")  //设置哪些路径，不需要验证的
                .permitAll()  //这些路径，设置不需要验证
                .anyRequest().authenticated();


        /*http.formLogin()// 开启表单认证
                .loginPage("/login.html") // 设置自定义的登录页面
                .and().authorizeRequests()
                .antMatchers("/login.html").permitAll()// 放行登录页面
                .anyRequest().authenticated();// 所有请求都需要登录认证才能访问*/

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
                //System.out.println(httpServletRequest.getParameter("username"));

                //从security框架中获取认证用户对象(LoginSecurityUserr)
                Object principal = authentication.getPrincipal();
                //把用户认证对象转换成json格式的string
                String datajson = JSON.toJSONString(principal);
                //把用户对象再封装到token中
                String token = jwtUntil.GetToken(datajson);

                // 设置响应头信息
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("utf-8");

                //封装token
                PmLoginResult pmLoginResult = new PmLoginResult(token, 28800L);
                //在封装成pmresult对象，响应给前端
                PmResult pmResult = PmResult.OK(pmLoginResult);
                String jsonString = JSON.toJSONString(pmResult);
                //把token数据响应给前端
                PrintWriter out = httpServletResponse.getWriter();
                out.write(jsonString);
                out.flush();
                out.close();
                //把token存到redis中，并设置8小时过期时间
                stringRedisTemplate.opsForValue().set("Token",token,60*60*8, TimeUnit.SECONDS);
            }
        };
        return chuliqi;
    }

    /**
     * 登录失败处理器
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            // 设置响应头信息
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            // 创建统一响应结果对象
            PmResult pmResult = new PmResult();
            if (exception instanceof BadCredentialsException) {
                pmResult = PmResult.error(CodeEnum.code_server_error.getCode(),"用户名或密码有误");
            } else if (exception instanceof UsernameNotFoundException) {
                pmResult = PmResult.error(CodeEnum.code_server_error.getCode(),"用户不存在");
            } else if (exception instanceof AccountExpiredException) {
                pmResult = PmResult.error(CodeEnum.code_server_error.getCode(),"帐号异常，请联系管理员");
            } else if (exception instanceof AccountStatusException) {
                pmResult = PmResult.error(CodeEnum.code_server_error.getCode(),"帐号异常，请联系管理员");
            } else if (exception instanceof InternalAuthenticationServiceException) {
                pmResult = PmResult.error(CodeEnum.code_server_error.getCode(),exception.getMessage());
            } else {
                pmResult = PmResult.error(CodeEnum.code_server_error);
            }

            // 返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(pmResult);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();

        };
    }

    /**
     * 登出成功处理器
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            // 设置响应头信息
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            // 从请求头中获取token
            String authorization = request.getHeader("Authorization");
            String token = authorization.replaceFirst("bearer ", "");
            // 将当前token从redis中删除
            stringRedisTemplate.delete("Token");


            // 返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(PmResult.OK(CodeEnum.code_ok));
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

}
