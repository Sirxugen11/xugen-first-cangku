package xg.demo.test.Controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.Pojo.PmPage;
import xg.demo.test.Pojo.PmResult;
import xg.demo.test.Pojo.PmRole;
import xg.demo.test.Server.SysRoleServer;

import java.util.List;

/**
 * 系统角色管理控制层
 */

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleServer sysRoleServer;

    /*查询所有角色信息的api*/
    @GetMapping("/list")
    public PmResult list(){
        List<PmRole> pmRoleList = sysRoleServer.GetAllPmrole();
        return PmResult.OK(pmRoleList);
    }

    /*分页查询角色列表的api*/
    @GetMapping("/page")
    public PmResult page(PmPage pmPage,@RequestParam(required = false,name = "roleName",defaultValue = "") String rolename){
        PmPage page = sysRoleServer.GetPmRolePage(pmPage, rolename);
        return PmResult.OK(page);
    }

    /**
     * 新增角色
     * @param PmRole 角色对象
     * @return
     */
    @PostMapping
    public PmResult saveSysRole(@RequestBody PmRole pmRole){
        int count = sysRoleServer.SavePmRole(pmRole);

        if(count > 0){
            return PmResult.OK(CodeEnum.code_ok);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }

    }

    /*系统管理-角色管理-编辑*/
    /**
     * 根据roleId标识查询角色详情
     * @param roleId    角色标识
     * @return
     */
    @GetMapping("/info/{roleid}")
    public PmResult info(@PathVariable("roleid") int roleid){
        PmRole pmRole = sysRoleServer.GetPmRoleByid(roleid);
        return PmResult.OK(pmRole);
    }

    /*系统管理-角色管理-编辑-确定*/
    /**
     * 修改角色信息
     * @param sysRole 角色对象
     * @return
     */
    @PutMapping
    public PmResult modifyPmRole(@RequestBody PmRole pmRole) {
        int i = sysRoleServer.modifyPmRole(pmRole);
        if (i >0){
            return PmResult.OK(CodeEnum.code_ok);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }

    }

    /*删除角色的api*/
    /**
     * 批量/单个删除角色
     * @param roleIdList 角色id集合
     * @return
     */
    @DeleteMapping
    public PmResult DeletePmRoleBylist(@RequestBody List<Integer> list){
        sysRoleServer.DeleteByRoleidList(list);
        return PmResult.OK(CodeEnum.code_ok);
    }




}
