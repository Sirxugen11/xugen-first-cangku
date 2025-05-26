package xg.demo.test.Server;

import xg.demo.test.Pojo.PmPage;

public interface SysLoginServer {

    //根据分页条件，进行分页查询，并返回PmPage对象的方法
    PmPage GetPmPage(PmPage pmPage,Integer userid,String operation);
}
