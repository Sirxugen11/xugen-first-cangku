package xg.demo.test.feginapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xg.demo.test.Pojo.PmResult;

@FeignClient("pm-business-test")
public interface testapi {

    @RequestMapping("/pm-business-test1")
    @ResponseBody
    public String pmbusinesstest1();
}
