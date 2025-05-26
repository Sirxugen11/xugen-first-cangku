package xg.demo.test.Server.imp;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xg.demo.test.Dao.PmMenuDao;
import xg.demo.test.Dao.PmRoleDao;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmRole;
import xg.demo.test.Server.SysRoleMenuServer;
import xg.demo.test.Server.SysRoleServer;
import xg.demo.test.Server.SysUserRoleService;

import java.util.Collections;
import java.util.List;

@Service
@CacheConfig(cacheNames="xg.demo.test.Server.imp.SysRoleServerImp")
@Slf4j
public class SysRoleServerImp implements SysRoleServer {
    @Autowired
    private PmRoleDao pmRoleDao;
    @Autowired
    private SysRoleMenuServer sysRoleMenuServer;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    //查询所有角色的方法
    //全量查询是需要将数据存放到缓存中
    @Cacheable(key="'GetAllPmrole'")
    @Override
    public List<PmRole> GetAllPmrole() {
        log.info("查询所有角色的方法的方法执行了！");
        List<PmRole> pmRoleList = pmRoleDao.GetAllPmrole();
        return pmRoleList;
    }

    //分页查询角色的方法
    @Override
    public PmPage GetPmRolePage(PmPage pmPage, String rolename) {
        //获取所有符合条件的角色数据总条数
        int roleCounts = pmRoleDao.GetPmRoleCounts(rolename);
        //
        PmPage resultpmPage = new PmPage();
        resultpmPage.setTotal(roleCounts);
        //
        List<PmRole> pmRoles = pmRoleDao.GetPmRolePage(pmPage, rolename);
        resultpmPage.setRecords(pmRoles);
        resultpmPage.setCurrent(pmPage.getCurrent());
        resultpmPage.setSize(pmPage.getSize());
        return resultpmPage;
    }

    //新增角色的方法
    /*1.新增角色
    2.新增对应角色权限的关系的数据*/
    @CacheEvict(key="'GetAllPmrole'")  //因为新增了角色，所以要删除原本在redis中的角色缓存
    @Override
    @Transactional
    public int SavePmRole(PmRole pmRole) {
        //1.
        int count1 = pmRoleDao.SavePmRole(pmRole);
        //2.
        List<Integer> menuIdList = pmRole.getMenuIdList();
        //通过角色名，获取刚新增角色的角色id
        int roleid = pmRoleDao.GetroleidByRolename(pmRole.getRoleName());
        sysRoleMenuServer.InsertBylist(menuIdList, roleid);
        return count1;
    }

    //根据roleid查询角色信息的方法
    @Override
    public PmRole GetPmRoleByid(int id) {
        //通过角色id获取对应的菜单权限id集合
        List<Integer> integers = sysRoleMenuServer.GetMenuidsByRoleid(id);
        //通过角色id获取角色信息
        PmRole pmRole = pmRoleDao.GetPmRoleByid(id);
        pmRole.setMenuIdList(integers);
        return pmRole;
    }

    //修改角色的方法
    //1.先清除角色对应的菜单权限，再重新赋予新的菜单权限
    //2.再更新角色的修改数据
    //注意：角色修改过了，就要清除redis中的角色缓存，不然你改了角色的名字，缓存不清，redis缓存里还是修改前的名字
    @CacheEvict(key="'GetAllPmrole'")
    @Override
    @Transactional
    public int modifyPmRole(PmRole pmRole) {
        //1.
        Integer roleId = pmRole.getRoleId();
        sysRoleMenuServer.CleanByRoleid(roleId);
        //再重新赋予权限
        List<Integer> menuIdList = pmRole.getMenuIdList();
        //判断是否有权限菜单
        if(CollectionUtil.isNotEmpty(menuIdList) && menuIdList.size() > 0) {
            sysRoleMenuServer.InsertBylist(menuIdList, roleId);
        }

        //2.
        int count = pmRoleDao.UpdatePmRole(pmRole);
        return count;

        //3.

    }

    /*批量或单个删除角色的方法
    1.先删除角色
    2.再删除角色权限表中的数据
    3.再删除用户角色表中的数据*/
    @Override
    @Transactional
    @CacheEvict(key="'GetAllPmrole'")
    public int DeleteByRoleidList(List<Integer> roleidlist) {
        //1.
        int count1 = pmRoleDao.DeleteByRoleidlist(roleidlist);
        //2.
        for (Integer temp : roleidlist) {
            sysRoleMenuServer.CleanByRoleid(temp);
        }
        //3.
        int count2 = sysUserRoleService.CleanByroleid(roleidlist);
        return 0;
    }


}
