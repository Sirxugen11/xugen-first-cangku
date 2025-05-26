package xg.demo.test.Server.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xg.demo.test.Dao.PmUserDao;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmUser;
import xg.demo.test.Server.SysUserRoleService;
import xg.demo.test.Server.SysUserServer;

import java.util.List;

@Service
@Slf4j
public class SysUserServerImp implements SysUserServer {
    @Autowired
    private PmUserDao pmUserDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<PmUser> GetPmUserPage(PmPage pmPage, String username) {
        List<PmUser> list = pmUserDao.GetPmUserPage(pmPage,username);
        return list;
    }

    @Override
    public int GetCountByusername(String username) {
        return pmUserDao.GetCountByusername(username);
    }

    @Transactional  //设计多次sql操作，为保证数据安全需要进行事务控制
    @Override
    public int InsertPmuser(PmUser pmUser) {
        if(pmUser == null){
            return 0;
        }
        //给注册的密码加密
        pmUser.setPassword(bCryptPasswordEncoder.encode(pmUser.getPassword()));
        //添加用户
        int count1 = pmUserDao.InsertPmuser(pmUser);

        //因为添加用户的时候userid是自增的，没有userid的数据，所以还要根据新增的用户名获取到新增用户的id
        int userid = pmUserDao.GetUseridByUsername(pmUser.getUsername());
        log.info("新增用户的userid是：{}",userid);
        //添加用户角色表数据
        //获取来自前端的角色id集合数据
        List<Integer> roleIdList = pmUser.getRoleIdList();
        if(roleIdList == null){
            return count1;
        }
        int count2 = sysUserRoleService.InsertBylist(userid, roleIdList);
        return count1 + count2;
    }


    /*修改管理员信息
     *  1.删除原有的管理员与角色关系记录
     *  2.添加新的管理员与角色关系记录
     *  3.修改管理员信息*/
    @Transactional
    @Override
    public Integer modifySysUserInfo(PmUser pmUser) {
        //1.
        Integer userId = pmUser.getUserId();
        int i = sysUserRoleService.CleanByuserid(userId);

        //2.
        List<Integer> roleIdList = pmUser.getRoleIdList();
        sysUserRoleService.InsertBylist(userId,roleIdList);

        //3.
        pmUser.setPassword(bCryptPasswordEncoder.encode(pmUser.getPassword()));
        int count = pmUserDao.UpdatePmuserInfo(pmUser);


        return count;
    }

    //批量或者单个，删除用户的api
    /*1.先删除用户表用户
    2.再删除用户角色表数据*/
    @Transactional
    @Override
    public int DeletePmusersByUseridlist(List<Integer> list) {
        //1.
        int count = pmUserDao.DeletePmusersByUseridlist(list);
        //2.
        for (Integer temp : list) {
            sysUserRoleService.CleanByuserid(temp);
        }
        return count;
    }

    /*根据用户id，查询用户信息的方法
    1.先根据id查出用户信息
    2.再根据用户id,从用户角色表里查出，用户拥有的角色权限*/
    @Override
    public PmUser GetPmUserByuserid(Integer userid) {
        //1.
        PmUser pmUser = pmUserDao.GetPmUserByuserid(userid);
        //2.
        List<Integer> roleidlist = sysUserRoleService.GetAllRoleidByUserid(userid);
        pmUser.setRoleIdList(roleidlist);
        return pmUser;
    }


}
