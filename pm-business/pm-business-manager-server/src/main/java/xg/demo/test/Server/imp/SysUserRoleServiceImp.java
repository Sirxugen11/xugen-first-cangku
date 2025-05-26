package xg.demo.test.Server.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xg.demo.test.Dao.PmUserRoleDao;
import xg.demo.test.Server.SysUserRoleService;

import java.util.List;

@Service
public class SysUserRoleServiceImp implements SysUserRoleService {
    @Autowired
    private PmUserRoleDao pmUserRoleDao;

    @Override
    public int InsertBylist(int userid, List<Integer> roleidlist) {
        return pmUserRoleDao.InsertBylist(userid,roleidlist);
    }

    @Override
    public int CleanByuserid(int userid) {
        int count = pmUserRoleDao.CleanByuserid(userid);
        return count;
    }

    /*根据角色id,清除用户角色表数据*/
    @Override
    public int CleanByroleid(List<Integer> list) {
        return pmUserRoleDao.CleanByroleid(list);
    }

    /*根据用户id,查询用户所拥有的角色id集合*/
    @Override
    public List<Integer> GetAllRoleidByUserid(int userid) {
        return pmUserRoleDao.GetAllRoleidByUserid(userid);
    }
}
