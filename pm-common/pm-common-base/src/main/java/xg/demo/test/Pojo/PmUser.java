package xg.demo.test.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*powermall项目用户表的实体类*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private Integer status;
    private String createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String shopId;

    //自定义属性,存放角色id的集合
    private List<Integer> roleIdList;

}
