import cn.doit.doit.day01._02_手机验证码;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.println(_02_手机验证码.getCode());
            Thread.sleep(20);
        }

    }
}
