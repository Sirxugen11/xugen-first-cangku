package xg.demo.test.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xg.demo.test.Enum.CodeEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmResult {
    //返回状态码
    private Integer code;
    //消息
    private String message;
    //数据
    private Object data;

    //ok的方法
    public static PmResult OK(CodeEnum codeEnum){
        return new PmResult(codeEnum.getCode(),codeEnum.getMessage(),null);
    }
    public static PmResult OK(CodeEnum codeEnum,Object data){
        return new PmResult(codeEnum.getCode(),codeEnum.getMessage(),data);
    }
    public static PmResult OK(Object data){
        return new PmResult(CodeEnum.code_ok.getCode(),CodeEnum.code_ok.getMessage(),data);
    }
    public static PmResult OK(Integer code,String message){
        return new PmResult(code,message,null);
    }

    //error的方法
    public static PmResult error(CodeEnum codeEnum){
        return new PmResult(codeEnum.getCode(),codeEnum.getMessage(),null);
    }
    public static PmResult error(CodeEnum codeEnum,Object data){
        return new PmResult(codeEnum.getCode(),codeEnum.getMessage(),data);
    }
    public static PmResult error(Object data){
        return new PmResult(CodeEnum.code_error.getCode(),CodeEnum.code_error.getMessage(),data);
    }
    public static PmResult error(Integer code,String message){
        return new PmResult(code,message,null);
    }

    /*public <T> T zhuanhuan(T t){
        return (T) this.data;
    }*/
}
