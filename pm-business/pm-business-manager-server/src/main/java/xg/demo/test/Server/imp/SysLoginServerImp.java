package xg.demo.test.Server.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xg.demo.test.Dao.PmLogDao;
import xg.demo.test.Pojo.PmLog;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Server.SysLoginServer;

import java.util.List;
@Service
public class SysLoginServerImp implements SysLoginServer {

    @Autowired
    private PmLogDao pmLogDao;

    //根据分页条件，进行分页查询，并返回PmPage对象的方法
    @Override
    public PmPage GetPmPage(PmPage pmPage, Integer userid, String operation) {
        PmPage result = new PmPage();
        //
        int count1 = pmLogDao.GetCounts(userid, operation);
        result.setTotal(count1);
        //
        List<PmLog> pmLoglist = pmLogDao.GetPmLogsByPage(pmPage, userid, operation);
        result.setRecords(pmLoglist);
        return result;
    }
}
