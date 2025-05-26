package xg.demo.test.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PmMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer menuId;
    private Integer parentId;
    private String name;
    private String url;
    private String perms;
    private Integer type;
    private String icon;
    private Integer orderNum;

    // 自定义属性，存放子集菜单的子节点集合
    private Set<PmMenu> list;

}
