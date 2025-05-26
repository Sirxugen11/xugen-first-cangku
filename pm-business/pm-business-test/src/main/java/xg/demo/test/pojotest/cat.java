package xg.demo.test.pojotest;

public class cat implements Animal{

    @Override
    public void eat() {
        System.out.println("猫吃饭！");
    }

    @Override
    public void sleep() {
        System.out.println("猫睡觉！");
    }
}
