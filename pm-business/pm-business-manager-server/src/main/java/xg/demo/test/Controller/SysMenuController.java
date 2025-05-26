package xg.demo.test.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.Pojo.PmMenu;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.Pojo.PmUser;
import xg.demo.test.SecurityPojo.LoginSecurityUserr;
import xg.demo.test.Server.SysMenuServer;
import xg.demo.test.Untils.AuthUtils;
import xg.demo.test.domain.PmMenuTreeAndPerms;

import java.util.List;
import java.util.Set;

/**
 * 系统权限控制层
 */

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuServer sysMenuServer;

    //查询用户的菜单权限和操作权限的api
    @GetMapping("/nav")
    public PmResult nav(){
        //利用自定义的工具类，获取当前登录对象（getLoginSecurityUserr）。然后获取里面userid
        LoginSecurityUserr loginSecurityUserr = AuthUtils.getLoginSecurityUserr();
        Integer userid = loginSecurityUserr.getPmUser().getUserId();
        //查询用户操作权限的集合
        Set<String> perms = loginSecurityUserr.getPerms();
        //获取菜单树
        Set<PmMenu> pmMenus = sysMenuServer.GetAllPmMenusByUserid(userid);
        //封装perms和pmMenus的数据，然后响应给前端
        PmMenuTreeAndPerms result = PmMenuTreeAndPerms.builder().authorities(perms).menuList(pmMenus).build();
        return PmResult.OK(result);
    }


    /**
     * 查询系统所有权限集合
     * @return  List<PmMenu> 所有权限集合
     */
    @GetMapping("/table")
    public PmResult table(){
        List<PmMenu> pmMenuList = sysMenuServer.GetAllPmMenus();
        return PmResult.OK(pmMenuList);
    }

    /**
     * 新增权限（菜单）的api
     * @param sysMenu 系统权限对象
     * @return
     */
    @PostMapping
    public PmResult savePmMenu(@RequestBody PmMenu pmMenu) {
        int count = sysMenuServer.InsertPmMenu(pmMenu);
        if(count > 0){
            return PmResult.OK(CodeEnum.code_ok);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }

    }


    /**
     * 根据menuId标识查询菜单权限信息
     * @param menuId 菜单权限标识
     * @return
     */
    @GetMapping("info/{menuid}")
    //@PreAuthorize("hasAuthority('sys:menu:info')")
    public PmResult loadPmMenuInfo(@PathVariable("menuid") Integer menuid) {
        PmMenu pmMenu = sysMenuServer.GetPmMenuByMenuid(menuid);
        return PmResult.OK(pmMenu);
    }

    /**
     * 修改菜单权限信息
     * @param PmMenu 菜单权限对象
     * @return
     */
    @PutMapping
    //@PreAuthorize("hasAuthority('sys:menu:update')")
    public PmResult modifyPmMenu(@RequestBody PmMenu pmMenu) {
        int count = sysMenuServer.UpdatePmMenu(pmMenu);
        //
        if(count > 0){
            return PmResult.OK(CodeEnum.code_ok);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }
    }


    /**
     * 删除菜单权限
     * @param menuid 菜单权限标识
     * @return
     */
    @DeleteMapping("/{menuid}")
    //@PreAuthorize("hasAuthority('sys:menu:delete')")
    public PmResult removePmMenu(@PathVariable("menuid") Integer menuid) {
        //Boolean removed = sysMenuService.removeSysMenuById(menuId);
        int count = sysMenuServer.DeletePmMenu(menuid);

        //
        if(count > 0){
            return PmResult.OK(CodeEnum.code_ok);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }
    }



}
