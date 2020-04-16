package NIO_09.decorator;

/**
 * 客户端调用装饰构件
 */
public class Client {
    public static void main(String[] args) {
//        包装两个具体装饰角色
        Component component = new ConcreteDecoratorA(new ConcreteDecoratorB(new ConcreteComponent()));
        /**
         * 功能A
         * 功能C
         * 功能B
         */
        component.doSomething();
//        只包装一个具体装饰角色
        component = new ConcreteDecoratorA(new ConcreteComponent());
        /**
         * 功能A
         * 功能B
         */
        component.doSomething();
//        不包装任何具体装饰角色，只使用基本类
        component = new ConcreteComponent();
        /**
         * 功能A
         */
        component.doSomething();
    }
}
