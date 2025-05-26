package xg.demo.test.CommFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.JstlUtils;
import xg.demo.test.SecurityPojo.LoginSecurityUserr;
import xg.demo.test.Untils.JwtUntil;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * token转换过滤器
 */
@Component
public class TokenTranslationFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwtUntil jwtUntil;
    /**
     * token转换过滤器
     *  前提：
     *  只负责处理携带token的请求，然后将认证的用户信息转换出来
     *  没有携带token的请求，交给security资源配置类中的处理器进行处理
     *
     *  1.获取token
     *  2.判断token是否有值
     *      有：
     *          token转换为用户信息
     *          将用户信息转换为security框架认识的用户信息对象
     *          再将认识的用户信息对象存放到当前资源服务的容器中
     *
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取Authorization的值，格式为:bearer token
        String authorizationValue = request.getHeader("Authorization");
        // 判断是否有值
        if (StringUtils.hasText(authorizationValue)) {
            // 获取token
            String token = authorizationValue.replaceFirst("bearer ", "");
            // 判断token是否有值
            if (StringUtils.hasText(token)) {
                // 解决token续签的问题
                // 从redis中获取token的存活时长
                ////Long expire = stringRedisTemplate.getExpire(AuthConstants.LOGIN_TOKEN_PREFIX + token);
                // 判断是否超过系统指定的阈值
                /*if (expire < AuthConstants.TOKEN_EXPIRE_THRESHOLD_TIME) {
                    // 给当前用户的token续签（本质就是增加token在redis中的存活时长）
                    stringRedisTemplate.expire(AuthConstants.LOGIN_TOKEN_PREFIX+token,AuthConstants.TOKEN_TIME, TimeUnit.SECONDS);
                }*/


                // 从redis中获取json格式字符串的认证用户信息
                String userJsonStr = stringRedisTemplate.opsForValue().get("Token");
                //解析token,获取token中存放LoginSecurityUserr对象json格式数据
                String datajson = jwtUntil.jiexi(token).getClaim("data").asString();
                // 将json格式字符串的认证用户信息转换为认证用户对象
                LoginSecurityUserr loginSecurityUserr = JSON.parseObject(datajson, LoginSecurityUserr.class);
                // 处理权限
                Set<SimpleGrantedAuthority> collect = loginSecurityUserr.getPerms().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
                // 创建UsernamePasswordAuthenticationToken对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginSecurityUserr,null,collect);

                // 将认证用户对象存放到当前模块的上下方中
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
