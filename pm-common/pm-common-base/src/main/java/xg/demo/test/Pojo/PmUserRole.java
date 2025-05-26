package xg.demo.test.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户角色表的id
    private Integer id;

    /**
     * 用户ID
     */

    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;
}
