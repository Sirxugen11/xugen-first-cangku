package xg.demo.test.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.Server.SysLoginServer;

/**
 * 系统操作日志管理控制层
 */

@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLoginServer sysLoginServer;

    /*多条件分页查询系统操作日志*/
    @GetMapping("/page")
    public PmResult page(PmPage pmPage, @RequestParam(value = "userid",required = false) Integer userid, @RequestParam(value = "operation",required = false) String operation){
        PmPage page = sysLoginServer.GetPmPage(pmPage, userid, operation);
        return PmResult.OK(page);
    }
}
