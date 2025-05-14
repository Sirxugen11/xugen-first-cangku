package xg.demo.test.Enum;
//自定义的code和message的枚举类
public enum CodeEnum {
    code_error(-1,"操作失败！"),
    code_ok(200,"操作成功！"),
    code_server_error(10000,"业务有错误！请检查业务流程及其数据是否正确！");

    private Integer code;
    private String message;

    //get方法
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    //构造方法
    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
