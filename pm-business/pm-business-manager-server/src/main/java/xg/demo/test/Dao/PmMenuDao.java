package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmMenu;

import java.util.List;
import java.util.Set;

public interface PmMenuDao {

    //根据userid查询对应角色所拥有的菜单权限的方法
    Set<PmMenu> GetAllPmMenusByUserid(@Param("userid") Integer userid);

    //查询系统所有权限集合
    List<PmMenu> GetAllPmMenus();

    //新增菜单权限的方法
    int InsertPmMenu(@Param("pmmenu") PmMenu pmMenu);

    //根据菜单id获取菜单权限信息的方法
    PmMenu GetPmMenuByMenuid(@Param("menuid") Integer menuid);

    //修改更新PmMenu表的方法
    int UpdatePmMenu(@Param("pmMenu") PmMenu pmMenu);

    //通过menu_id，查询它有多少条子菜单的方法
    int GetSonPmMenuBymenuid(@Param("menuid") Integer menuid);

    //根据menu_id，删除菜单权限数据的方法
    int DeleteByMenuid(@Param("menuid") Integer menuid);
}
