package xg.demo.test.Server.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xg.demo.test.Dao.PmMenuDao;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.Expection.BusinessExecption;
import xg.demo.test.Pojo.PmMenu;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.Server.SysMenuServer;
import xg.demo.test.Server.SysRoleMenuServer;
import xg.demo.test.Server.SysRoleServer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//菜单操作相关的service层接口实现类

@CacheConfig(cacheNames="xg.demo.test.Server.imp.SysMenuServerImp")
@Service
@Slf4j
public class SysMenuServerImp implements SysMenuServer {
    @Autowired
    private PmMenuDao pmMenuDao;
    @Autowired
    private SysRoleMenuServer sysRoleMenuServer;

    //根据userid查询对应角色所拥有的菜单权限的方法
    @Override
    @Cacheable(key="'key:userid'") //redis注解式缓存到redis中
    //@CacheEvict(key="'key:userid'")  //清除缓存
    public Set<PmMenu> GetAllPmMenusByUserid(Integer userid) {
       log.info("xg.demo.test.Server.imp.SysMenuServerImp的GetAllPmMenusByUserid执行了！");
       Set<PmMenu> setlist = pmMenuDao.GetAllPmMenusByUserid(userid);

       /*给setlist,根据它的parent_id生成菜单树。
        所有的一级菜单他们的pid都是0*/
        Set<PmMenu> pmMenuslist = CreatePmMenuTree(setlist, 0);
        return pmMenuslist;
    }

    //查询系统所有菜单权限集合
    @Cacheable(key="'GetAllPmMenus'") //redis注解式缓存到redis中
    @Override
    public List<PmMenu> GetAllPmMenus() {
        return pmMenuDao.GetAllPmMenus();
    }

    /*新增菜单权限的方法*/
    @CacheEvict(key="'GetAllPmMenus'")
    @Override
    public int InsertPmMenu(PmMenu pmMenu) {
        return pmMenuDao.InsertPmMenu(pmMenu);
    }

    //根据菜单id获取菜单权限信息的方法
    @Override
    public PmMenu GetPmMenuByMenuid(Integer menuid) {
        return pmMenuDao.GetPmMenuByMenuid(menuid);
    }

    /*修改更新PmMenu表的方法*/
    @Override
    @CacheEvict(key="'GetAllPmMenus'")
    public int UpdatePmMenu(PmMenu pmMenu) {
        return pmMenuDao.UpdatePmMenu(pmMenu);
    }

    //删除PmMenu菜单权限的方法
    /*注意点：1.删除菜单的时候，需要重置redis中权限菜单的缓存
            2.如果要删除的菜单它下面还有很多子菜单需要弹窗提示，不允许直接删除这个菜单
            3.删除菜单权限表中的数据的时候，同时也要删除角色菜单权限中对应的数据*/
    @CacheEvict(key="'GetAllPmMenus'")
    @Transactional
    @Override
    public int DeletePmMenu(Integer menuid) {
        int resultcount = 0;

        //2.
        int count1 = pmMenuDao.GetSonPmMenuBymenuid(menuid);
        if(count1 > 0){
            log.info("删除的菜单中，包含一个或者多个子菜单，无法直接删除！请先删除它的子菜单！");
            throw new BusinessExecption("删除的菜单中，包含一个或者多个子菜单，无法直接删除！请先删除它的子菜单！");
        }else {
           resultcount =  pmMenuDao.DeleteByMenuid(menuid);
        }

        //3.
        int count2 = sysRoleMenuServer.CleanByMenuid(menuid);

        return resultcount;
    }


    //生成菜单树的方法
    Set<PmMenu> CreatePmMenuTree(Set<PmMenu> allList, Integer pid){
        HashSet<PmMenu> Onelist = new HashSet<>();
        //父级菜单
        for (PmMenu temp : allList) {
            if(temp.getParentId() == pid){
                Onelist.add(temp);
            }
        }

        //子级菜单
        for (PmMenu temp : Onelist) {
            Set<PmMenu> pmMenus = CreatePmMenuTree(allList, temp.getMenuId());
            temp.setList(pmMenus);
        }

        return Onelist;
    }
}
