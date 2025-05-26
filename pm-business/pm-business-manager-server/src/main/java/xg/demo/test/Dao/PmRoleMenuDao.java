package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmMenu;

import java.util.List;

public interface PmRoleMenuDao {
    //新增数据的方法
    int InsertBylist(@Param("list") List<Integer> list,@Param("roleid") int roleid);

    //根据roleid查询对应角色所有菜单权限id的方法
    List<Integer> GetMenuidsByRoleid(@Param("roleid") int id);

    //根绝roleid清除，角色对应菜单权限的方法
    int CleanByRoleid(@Param("roleid") int roleid);

    //根据menuid清除，角色对应菜单权限的方法
    int CleanByMenuid(@Param("menuid") Integer menuid);
}
