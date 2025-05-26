package xg.demo.test.msController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.Pojo.PmResult;

/**
 * 后台管理系统维护会员控制层
 */

@RestController
@RequestMapping("/admin/user")
public class SysMemberController {

    @GetMapping("/page")
    public PmResult page(@RequestParam("current") int current,@RequestParam("size") int size){

        return PmResult.OK(CodeEnum.code_ok);
    }
}
