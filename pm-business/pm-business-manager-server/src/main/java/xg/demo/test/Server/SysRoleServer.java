package xg.demo.test.Server;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmRole;
import xg.demo.test.Pojo.PmUser;

import java.util.List;

public interface SysRoleServer {

    //查询所有角色信息的方法
    List<PmRole> GetAllPmrole();

    //分页查询角色的方法
    PmPage GetPmRolePage(PmPage pmPage,String rolename);

    //新增角色的方法
    int SavePmRole(PmRole pmRole);

    //根据roleid查询角色信息的方法
    PmRole GetPmRoleByid(int id);

    //修改角色的方法
    int modifyPmRole(PmRole pmRole);

    //批量或单个删除角色的方法
    int DeleteByRoleidList(List<Integer> roleidlist);

}
