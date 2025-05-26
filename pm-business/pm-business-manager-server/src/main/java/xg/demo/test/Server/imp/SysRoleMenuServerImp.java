package xg.demo.test.Server.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import xg.demo.test.Dao.PmRoleMenuDao;
import xg.demo.test.Server.SysRoleMenuServer;

import java.util.List;

@Service
@CacheConfig(cacheNames = "xg.demo.test.Server.imp.SysRoleMenuServerImp")
public class SysRoleMenuServerImp implements SysRoleMenuServer {
    @Autowired
    private PmRoleMenuDao pmRoleMenuDao;

    //新增数据的方法
    @Override
    public int InsertBylist(List<Integer> list, int roleid) {
        return pmRoleMenuDao.InsertBylist(list,roleid);
    }

    //根据roleid查询对应角色所有菜单权限id的方法
    @Override
    public List<Integer> GetMenuidsByRoleid(int id) {

        return pmRoleMenuDao.GetMenuidsByRoleid(id);
    }

    //根绝roleid清除，角色对应菜单权限的方法
    @Override
    public int CleanByRoleid(int roleid) {
        int i = pmRoleMenuDao.CleanByRoleid(roleid);
        return i;
    }

    //根据menuid清除，角色对应菜单权限的方法
    @Override
    public int CleanByMenuid(Integer menuid) {
        return pmRoleMenuDao.CleanByMenuid(menuid);
    }


}
