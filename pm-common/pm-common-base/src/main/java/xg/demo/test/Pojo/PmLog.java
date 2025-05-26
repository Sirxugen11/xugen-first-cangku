package xg.demo.test.Pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 系统日志
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PmLog implements Serializable {

    private Integer id;

    /**
     * 用户名
     */

    private Integer userId;

    /**
     * 用户操作
     */

    private String operation;

    /**
     * 请求方法
     */

    private String method;

    /**
     * 请求参数
     */

    private String params;

    /**
     * 执行时长(毫秒)
     */

    private Integer time;

    /**
     * IP地址
     */

    private String ip;

    /**
     * 创建时间
     */

    private Date createDate;

    private static final long serialVersionUID = 1L;
}