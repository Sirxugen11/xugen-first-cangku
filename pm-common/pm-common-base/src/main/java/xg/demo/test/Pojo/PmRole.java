package xg.demo.test.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色的包装类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmRole implements Serializable {
    private  long serialVersionUID = 1L;


    //角色id
    private Integer roleId;

    /**
     * 角色名称
     */

    private String roleName;

    /**
     * 备注
     */

    private String remark;

    /**
     * 创建者ID
     */

    private Integer createUserId;

    /**
     * 创建时间
     */

    private Date createTime;

    //////////////// 新增角色集合 自定义属性 ////////////////////
    private List<Integer> menuIdList;


}
