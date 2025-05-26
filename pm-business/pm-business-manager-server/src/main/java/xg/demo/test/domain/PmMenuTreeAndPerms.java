package xg.demo.test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xg.demo.test.Pojo.PmMenu;

import java.io.Serializable;
import java.util.Set;

/*封装，操作权限的集合和菜单树的包装类*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmMenuTreeAndPerms implements Serializable {
    /*操作权限集合*/
    private Set<String> authorities;
    /*菜单权限集合*/
    private Set<PmMenu> menuList;
}
