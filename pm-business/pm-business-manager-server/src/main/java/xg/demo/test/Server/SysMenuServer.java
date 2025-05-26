package xg.demo.test.Server;

import xg.demo.test.Pojo.PmMenu;

import java.util.List;
import java.util.Set;

//菜单操作相关的service层接口
public interface SysMenuServer {

    //根据userid查询对应角色所拥有的菜单权限的方法
    Set<PmMenu> GetAllPmMenusByUserid(Integer userid);

    //查询系统所有权限集合
    List<PmMenu> GetAllPmMenus();

    //新增菜单权限的方法
    int InsertPmMenu(PmMenu pmMenu);

    //根据菜单id获取菜单权限信息的方法
    PmMenu GetPmMenuByMenuid(Integer menuid);


    //修改更新PmMenu表的方法
    int UpdatePmMenu(PmMenu pmMenu);

    //删除PmMenu菜单权限的方法
    /*注意点：1.删除菜单的时候，需要重置redis中权限菜单的缓存
            2.删除菜单权限表中的数据的时候，同时也要删除角色菜单权限中对应的数据
            3.如果要删除的菜单它下面还有很多子菜单需要弹窗提示，不允许直接删除这个菜单*/
    int DeletePmMenu(Integer menuid);
}
