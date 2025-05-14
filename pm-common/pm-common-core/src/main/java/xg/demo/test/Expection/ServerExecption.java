package xg.demo.test.Expection;
/*自定义业务异常*/

public class ServerExecption extends RuntimeException {
    public ServerExecption() {
        super();
    }

    public ServerExecption(String message) {
        super(message);
    }
}
