package xg.demo.test.Dao;

import org.apache.ibatis.annotations.Param;
import xg.demo.test.Pojo.PmLog;
import xg.demo.test.Pojo.PmPage;

import java.util.List;

public interface PmLogDao {

    //根据分页条件，进行分页查询，并返回PmPage对象的方法
    List<PmLog> GetPmLogsByPage(@Param("page") PmPage pmPage, @Param("userid") Integer userid, @Param("operation") String operation);

    //查询符合查询条件的数据总数
    int GetCounts(@Param("userid") Integer userid, @Param("operation") String operation);

}
