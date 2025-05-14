package xg.demo.test.Untils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JwtUntil {

    private static Map<String,Object> map = new HashMap<>();


    static {
        map.put("alg", "HS256");
        map.put("typ", "JWT");
    }

    //得到当前的系统时间
    Date currentDate = new Date();
    //过期时间
    Date expTime = null;

    //jwt加密生成token
    public String GetToken(String userid,String username,String usercode){
        //设置过期时间（单位是毫秒，所以*1000）
        expTime = new Date(new Date().getTime() + Long.valueOf(3600*8*1000));
        //生成token
        String token = JWT.create().withHeader(map)  //头
                .withIssuedAt(currentDate) //创建时间
                .withExpiresAt(expTime)  //过期时间
                .withClaim("username", username)  //自定义数据
                .withClaim("userid", userid)  //自定义数据
                .withClaim("flag","ok")  //自定义数据
                .withClaim("usercode",usercode)  //自定义数据
                .sign(Algorithm.HMAC256("xugen666"));
        //
        return token;
    }
    //生成token(方法重载)
    public String GetToken(Object data){
        //设置过期时间（单位是毫秒，所以*1000）
        expTime = new Date(new Date().getTime() + Long.valueOf(3600*8*1000));
        //生成token
        String token = JWT.create().withHeader(map)  //头
                .withIssuedAt(currentDate) //创建时间
                .withExpiresAt(expTime)  //过期时间
                .withClaim("data", JSON.toJSONString(data))
                .sign(Algorithm.HMAC256("xugen666"));
        //
        return token;
    }
    //生成token(方法重载)
    public String GetToken(String datajson){
        //设置过期时间（单位是毫秒，所以*1000）
        expTime = new Date(new Date().getTime() + Long.valueOf(3600*8*1000));
        //生成token
        String token = JWT.create().withHeader(map)  //头
                .withIssuedAt(currentDate) //创建时间
                .withExpiresAt(expTime)  //过期时间
                .withClaim("data", datajson)
                .sign(Algorithm.HMAC256("xugen666"));
        //
        return token;
    }

    //jwt校验解析token
    public DecodedJWT jiexi(String token){
        //获取jwt解析器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("xugen666")).build();
        //解析token,获取解析出来的对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);


        /*//获取token中的role的值，如果类型是int,就需要用asint()方法转换
        String role = decodedJWT.getClaim("role").asString();*/
        return decodedJWT;
    }
}
