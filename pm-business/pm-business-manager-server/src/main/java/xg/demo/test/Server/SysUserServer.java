package xg.demo.test.Server;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmUser;

import java.util.List;

//用户操作相关的service层接口
public interface SysUserServer {
    //获取用户分页数据的方法
    List<PmUser> GetPmUserPage(PmPage pmPage,String username);

    //按条件查询用户总数的方法
    int GetCountByusername(String username);

    //新增用户的方法
    int InsertPmuser(PmUser pmUser);

    /**
     * 修改管理员信息
     * @param pmUser
     * @return
     */
    Integer modifySysUserInfo(PmUser pmUser);

    //批量或者单个，删除用户的api
    int DeletePmusersByUseridlist(List<Integer> list);

    //根据用户id，查询用户信息的方法
    PmUser GetPmUserByuserid(Integer userid);

}
