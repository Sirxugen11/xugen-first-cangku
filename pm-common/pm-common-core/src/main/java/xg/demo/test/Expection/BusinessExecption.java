package xg.demo.test.Expection;

public class BusinessExecption extends RuntimeException{
    public BusinessExecption() {
        super();
    }

    public BusinessExecption(String message) {
        super(message);
    }
}
