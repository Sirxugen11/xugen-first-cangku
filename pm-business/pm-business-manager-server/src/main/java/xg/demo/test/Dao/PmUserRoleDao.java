package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmUserRoleDao {

    //新增数据的方法
    int InsertBylist(@Param("userid") int userid, @Param("roleidlist")List<Integer> roleidlist);

    //根据用户id清除，用户对应角色的方法
    int CleanByuserid(@Param("userid") int userid);

    //根据角色id,清除用户角色表数据
    int CleanByroleid(@Param("list") List<Integer> list);

    //根绝用户id,查询用户所拥有的角色id集合
    List<Integer> GetAllRoleidByUserid(@Param("userid") int userid);
}
