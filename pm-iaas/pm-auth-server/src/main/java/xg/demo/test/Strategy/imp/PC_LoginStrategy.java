package xg.demo.test.Strategy.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xg.demo.test.Constant.AuthConstant;
import xg.demo.test.Dao.PmMenuDao;
import xg.demo.test.Dao.PmUserDao;
import xg.demo.test.Pojo.PmUser;
import xg.demo.test.SecurityPojo.LoginSecurityUserr;
import xg.demo.test.Strategy.LoginStrategy;

import java.util.List;
import java.util.Set;

/*PC端系统登录策略的具体实现*/
@Service(AuthConstant.PC_USER_LOGIN)
public class PC_LoginStrategy implements LoginStrategy {
    @Autowired
    private PmUserDao pmUserDao;
    @Autowired
    private PmMenuDao pmMenuDao;

    @Override
    public UserDetails realLogin(String username) {
        System.out.println("pc端的认证流程开始：");
        //根据用户名查询用户信息
        PmUser pmUser = pmUserDao.GetPmUser_byusername(username);
        if(pmUser == null ){
            throw new UsernameNotFoundException("登录的用户不存在！");
        }
        LoginSecurityUserr loginSecurityUserr = new LoginSecurityUserr();
        loginSecurityUserr.setPmUser(pmUser);
        Set<String> strings = pmMenuDao.GetAllPerms_byuserid(pmUser.getUserId());
        loginSecurityUserr.setPerms(strings);
        //loginSecurityUserr.getPerms().size();
        return loginSecurityUserr;
    }
}
