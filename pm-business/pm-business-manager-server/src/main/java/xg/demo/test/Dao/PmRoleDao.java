package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmMenu;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmRole;
import xg.demo.test.Pojo.PmUser;

import java.util.List;

public interface PmRoleDao {

    //查询所有角色的方法
    List<PmRole> GetAllPmrole();

    //分页查询角色的方法
    List<PmRole> GetPmRolePage(@Param("page") PmPage pmPage, @Param("rolename") String rolename);

    //查询角色总数的方法
    int GetPmRoleCounts(@Param("rolename") String rolename);

    //新增角色的方法
    int SavePmRole(@Param("pmrole") PmRole pmRole);

    //根据角色姓名查询角色id的方法
    int GetroleidByRolename(@Param("rolename") String rolename);

    //根据roleid查询角色信息的方法
    PmRole GetPmRoleByid(@Param("roleid") int id);

    //更新角色的方法
    int UpdatePmRole(@Param("pmrole") PmRole pmRole);

    //批量或单个删除角色的方法
    int DeleteByRoleidlist(@Param("list") List<Integer> list);


}
