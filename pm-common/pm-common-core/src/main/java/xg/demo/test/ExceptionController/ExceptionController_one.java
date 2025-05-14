package xg.demo.test.ExceptionController;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xg.demo.test.Enum.CodeEnum;
import xg.demo.test.Expection.ServerExecption;
import xg.demo.test.Pojo.PmResult;

@RestControllerAdvice
public class ExceptionController_one {

    //ServerExecption异常的处理方法
    @ExceptionHandler(ServerExecption.class)
    public PmResult ExceptionHandler1(ServerExecption e){
        //获取异常的信息
        String message = e.getMessage();
        if(StringUtils.hasText(message)) {
            //如果异常有异常信息，就返回本身的异常信息
            return PmResult.error(CodeEnum.code_server_error.getCode(), message);
        }else {
            //如果异常没有异常信息，就返回默认的异常数据
            return PmResult.error(CodeEnum.code_server_error);
        }
    }

    //
    @ExceptionHandler(RuntimeException.class)
    public PmResult ExceptionHandler2(RuntimeException e){
        String message = e.getMessage();
        if(StringUtils.hasText(message)) {
            return PmResult.error(CodeEnum.code_error.getCode(), message);
        }else {
            return PmResult.error(CodeEnum.code_error);
        }
    }

}
