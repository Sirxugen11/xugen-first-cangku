/*
package xg.demo.test.pmbt_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.feginapi.testapi;

import javax.servlet.http.HttpServletRequest;

@RestController
public class test implements testapi {
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/applicationContexttest")
    public PmResult applicationContexttest(){
        return PmResult.OK(CodeEnum.code_ok);
    }

    @Override
    public String pmbusinesstest1() {
        String authorization = request.getHeader("Authorization");
        return authorization;
    }
}
*/
