package xg.demo.test.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*powermall项目用户表的实体类*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmUser {

    private Integer userid;
    private String username;
    private String pmpassword;
    private String email;
    private String mobile;
    private Integer status;
    private String createuserid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String shopid;
}
