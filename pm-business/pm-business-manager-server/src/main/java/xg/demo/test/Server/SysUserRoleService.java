package xg.demo.test.Server;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleService {
    //新增数据的方法
    int InsertBylist(int userid,List<Integer> roleidlist);

    //根据用户id清除，用户对应角色的方法
    int CleanByuserid(int userid);

    //根据角色id,清除用户角色表数据
    int CleanByroleid(List<Integer> list);

    //根据用户id,查询用户所拥有的角色id集合
    List<Integer> GetAllRoleidByUserid(int userid);
}
