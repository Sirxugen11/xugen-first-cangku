package xg.demo.test.Controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xg.demo.test.Dao.PmMenuDao;
import xg.demo.test.Dao.PmUserDao;
import xg.demo.test.Factory.LoginStrategyFactory;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.Pojo.PmUser;
import xg.demo.test.SecurityPojo.LoginSecurityUserr;
import xg.demo.test.Untils.JwtUntil;
import xg.demo.test.feginapi.testapi;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.event.PaintEvent;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class Auth_Server_test {
    @Autowired
    private JwtUntil jwtUntil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PmUserDao pmUserDao;
    @Autowired
    private PmMenuDao pmMenuDao;
    @Autowired
    private LoginStrategyFactory loginStrategyFactory;
    @Autowired
    private testapi api;


    @GetMapping("/test1")
    @ResponseBody
    public String test1(HttpServletRequest request){
        /*PmUser gly = pmUserDao.GetPmUser_byusername("admin");
        System.out.println(gly);
        System.out.println(pmMenuDao.GetAllPerms_byuserid(gly.getUserid()).size());
        System.out.println(loginStrategyFactory.loginStrategyMap.size());
        System.out.println(bCryptPasswordEncoder.matches("123456", gly.getPmpassword()));*/

        System.out.println(request.getParameter("username"));
        return "test1";
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2(HttpServletRequest request){
        log.info("浏览器传过来的请求携带的token:{}",request.getHeader("Authorization"));
        String token = request.getHeader("Authorization").replaceFirst("bearer ", "");
        String datajson = jwtUntil.jiexi(token).getClaim("data").asString();
        System.out.println(JSON.parseObject(datajson, LoginSecurityUserr.class));
        String s = api.pmbusinesstest1();
        return s;
    }

    @GetMapping("/CreateToken")
    @ResponseBody
    public PmResult CreateToken(HttpServletResponse response, HttpServletRequest request){
        String token = jwtUntil.GetToken("1", "gly", "1");
        /*Cookie cookie = new Cookie("Token", token);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);*/
        log.info("token:{}。创建时间为{}",token,new Date());
        stringRedisTemplate.opsForValue().set("Token",token,60*60*8,TimeUnit.SECONDS);
        return PmResult.OK(token);
    }

    //注册用户
    @GetMapping("/zhuece")
    public PmResult zhuce(@RequestParam("username") String username,@RequestParam("password") String password){

        return PmResult.OK(bCryptPasswordEncoder.encode(password));
    }

    //测试jwtuntil工具类
    @GetMapping("/jwtuntil")
    public PmResult jwtuntil(){
        PmUser p1 = new PmUser();
        PmUser p2 = new PmUser();
        p2.setUserId(1);
        System.out.println(jwtUntil.GetToken(p1));
        System.out.println(jwtUntil.GetToken(p2));
        String data = jwtUntil.jiexi(jwtUntil.GetToken(p2)).getClaim("data").asString();
        return PmResult.OK(data);
    }

    //测试redis
    @GetMapping("/redis/gettoken")
    public PmResult redis(){
        Boolean token = stringRedisTemplate.hasKey("Token");
        return PmResult.OK(token);
    }
    //测试redis
    @GetMapping("/redis/tokentest2")
    public String tokentest2(){
        String test = jwtUntil.GetToken("test");
        //设置在redis中1小时过期
        stringRedisTemplate.opsForValue().set("test2",test,60*60,TimeUnit.SECONDS);
        return "ok";
    }
    //测试redis
    @GetMapping("/redis/tokentest1")
    public String tokentest1(){
        //获取键值对,在redis中的过期时间
        Long expiretime = stringRedisTemplate.getExpire("test2", TimeUnit.SECONDS);
        //
        log.info(String.valueOf(expiretime));
        //重置键值对在redis中的过期时间
        stringRedisTemplate.expire("test2",60*30,TimeUnit.SECONDS);
        //
        Long expiretime2 = stringRedisTemplate.getExpire("test2", TimeUnit.SECONDS);
        log.info(String.valueOf(expiretime2));
        return "ok";
    }

    //测试异常处理器1
    /*@RequestMapping("/execptioncontrollertest1/{flag}")
    public PmResult execptioncontrollertest1(@PathVariable("flag") String flag){
        if(StringUtils.hasText(flag)) {
            throw new ServerExecption();
        }
        return PmResult.OK(CodeEnum.code_ok);
    }*/

    //测试异常处理器2
   /* @RequestMapping("/execptioncontrollertest2/{flag}")
    public PmResult execptioncontrollertest2(@PathVariable("flag") String flag){
        if(StringUtils.hasText(flag)) {
            throw new RuntimeException("运行时异常");
        }
        return PmResult.OK(CodeEnum.code_ok);
    }*/

}
