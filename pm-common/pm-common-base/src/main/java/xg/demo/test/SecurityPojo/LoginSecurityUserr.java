package xg.demo.test.SecurityPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xg.demo.test.Pojo.PmUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//用户认证对象
/*其他服务也会用到这个给他放到公共的地方去*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSecurityUserr implements UserDetails {
    /*private Integer userid;
    private String username;
    private String pm_password;
    private String email;
    private String mobile;
    private Integer status;
    private String createuserid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String shopid;*/

    //
    private PmUser pmUser;

    // 商城购物系统会员的相关属性
    private String openid;
    //权限
    //注意权限可能会有重复的情况，所以这里不用list而用set集合
    private Set<String> perms;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {

        return this.pmUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.pmUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //
    public Set<String> getPerms() {
        Set<String> list = new HashSet<>();
        //一条Perms数据里可以通过","隔开写多个权限，所以需要将他们拆解再重新汇总下
        for (String perm : this.perms) {
            if(perm.contains(",")){
                // 包含：说明一条权限里有多个权限
                // 根据,号进行分隔处理
                String[] split = perm.split(",");
                for (String s : split) {
                    list.add(s);
                }
            }
            //不包含","的话，说明就一条权限数据
            list.add(perm);
        }
        return list;
    }
}
