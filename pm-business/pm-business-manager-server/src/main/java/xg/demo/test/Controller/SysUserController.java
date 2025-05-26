package xg.demo.test.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.Pojo.PmUser;
import xg.demo.test.Server.SysUserRoleService;
import xg.demo.test.Server.SysUserServer;
import xg.demo.test.Untils.AuthUtils;

import java.util.List;

/**
 * 系统管理员控制层
 */

@RestController
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {
    @Autowired
    private SysUserServer sysUserServer;

    /*查询登录的用户信息的api*/
    @GetMapping("/info")
    public PmResult info(){
        //通过AuthUtils工具类，获取用户(PmUser)对象
        PmUser pmUser = AuthUtils.getLoginSecurityUserr().getPmUser();
        return PmResult.OK(pmUser);
    }

    /*多条件分页查询系统管理员*/
    @GetMapping("/page")
    //@PreAuthorize("hasAuthority('sys:user:page')")  springsercuity框架的注解，用于校验权限的
    public PmResult page(PmPage pmPage, @RequestParam(required = false,value = "username",defaultValue = "") String username){
        log.info("接收到的pmpage数据是：{}。接收到的username数据是：{}",pmPage,username);
        List<PmUser> pmUserList = sysUserServer.GetPmUserPage(pmPage, username);
        //获取分页查询的数据
        PmPage resultpage = new PmPage();
        resultpage.setRecords(pmUserList);
        //获取分页查询的总数
        int count = sysUserServer.GetCountByusername(username);
        resultpage.setTotal(count);
        return PmResult.OK(resultpage);
    }

    /*新增用户的api*/
    @PostMapping
    //@PreAuthorize("hasAuthority('sys:user:save')")
    public PmResult saveSysUser(@RequestBody PmUser pmUser) {
        int count = sysUserServer.InsertPmuser(pmUser);
        if(count > 0){
            return PmResult.OK(CodeEnum.code_ok);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }
    }


    /*修改管理员信息*/
    @PutMapping
    //@PreAuthorize("hasAuthority('sys:user:update')")
    public PmResult modifySysUserInfo(@RequestBody PmUser pmUser) {
        Integer count = sysUserServer.modifySysUserInfo(pmUser);
        return PmResult.OK(CodeEnum.code_ok);
    }

    /*批量/单个，删除用户的api
    参数是通过路径传参的多个userid*/
    @DeleteMapping("/{useridlist}")
    public PmResult removePmUsers(@PathVariable("useridlist") List<Integer> useridlist){
        //
        int count = sysUserServer.DeletePmusersByUseridlist(useridlist);
        if(count >0){
            return PmResult.OK(CodeEnum.code_ok);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }
    }

    /**
     * 根据标识查询系统管理员信息
     * @param id    管理员标识
     * @return
     */
    @GetMapping("/info/{userid}")
    public PmResult info(@PathVariable("userid") Integer userid){
        PmUser pmUser = sysUserServer.GetPmUserByuserid(userid);
        return PmResult.OK(pmUser);
    }
}
