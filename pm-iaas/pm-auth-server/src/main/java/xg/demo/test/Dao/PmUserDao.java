package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmUser;

public interface PmUserDao {

    PmUser GetPmUser_byusername(@Param("username") String username);
}
