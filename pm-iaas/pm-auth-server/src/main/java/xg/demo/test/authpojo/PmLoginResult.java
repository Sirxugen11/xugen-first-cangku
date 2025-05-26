package xg.demo.test.authpojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//登录统一响应数据结果对象
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmLoginResult {

    //存放token的属性
    private String accessToken;

    //存放token过期时间的属性
    private Long expireIn;
}
