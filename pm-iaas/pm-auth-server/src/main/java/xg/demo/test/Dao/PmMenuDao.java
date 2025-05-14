package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PmMenuDao {
    //根据用户id多表联查获取用户的所有权限数据
    //注意权限可能会有重复的情况，所以这里不用list而用set集合
    Set<String> GetAllPerms_byuserid(@Param("userid") Integer userid);
}
