package xg.demo.test.Server;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuServer {

    //新增数据的方法
    int InsertBylist(List<Integer> list, int roleid);

    //根据roleid查询对应角色所有菜单权限id的方法
    List<Integer> GetMenuidsByRoleid(int id);

    //根绝roleid清除，角色对应菜单权限的方法
    int CleanByRoleid(int roleid);

    //根据menuid清除，角色对应菜单权限的方法
    int CleanByMenuid(Integer menuid);
}
