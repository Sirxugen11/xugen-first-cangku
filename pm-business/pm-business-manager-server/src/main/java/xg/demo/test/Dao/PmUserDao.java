package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmUser;

import java.util.List;

public interface PmUserDao {

    //获取用户分页的方法
    List<PmUser> GetPmUserPage(@Param("page")PmPage pmPage,@Param("username") String username);

    //按条件查询用户总数的方法
    int GetCountByusername(@Param("username") String username);

    //新增用户的方法
    int InsertPmuser(@Param("pmuser") PmUser pmUser);

    //根据用户名查询用户id
    int GetUseridByUsername(@Param("username") String username);

    //修改用户信息的方法
    int UpdatePmuserInfo(@Param("pmuser") PmUser pmUser);

    //批量或者单个，删除用户的api
    int DeletePmusersByUseridlist(@Param("useridlist") List<Integer> list);

    //根据userid查询用户信息
    PmUser GetPmUserByuserid(@Param("userid") Integer userid);
}
