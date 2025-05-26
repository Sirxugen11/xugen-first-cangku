package xg.demo.test.Untils;

import org.springframework.security.core.context.SecurityContextHolder;
import xg.demo.test.SecurityPojo.LoginSecurityUserr;

import java.util.Set;

/**
 * 认证授权工具类
 */
public class AuthUtils {


    /**
     * 获取Security容器中的认证用户对象
     * @return
     */
    public static LoginSecurityUserr getLoginSecurityUserr() {
        return (LoginSecurityUserr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取Security容器中认证用户对象标识
     * @return
     */
    public static Integer getUserId() {
        return getLoginSecurityUserr().getPmUser().getUserId();
    }

    /**
     * 获取Security容器中认证用户对象的操作权限集合
     * @return
     */
    public static Set<String> getLoginUserPerms() {
        return getLoginSecurityUserr().getPerms();
    }

    /**
     * 获取Security容器中认证用户对象的openid
     * @return
     */
    public static String getMemberOpenId() {
        return getLoginSecurityUserr().getOpenid();
    }
}
