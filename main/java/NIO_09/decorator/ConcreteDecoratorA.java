package NIO_09.decorator;

/**
 * 具体装饰角色
 * 1 继承装饰角色
 * 2 重写并增强父类的方法
 */
public class ConcreteDecoratorA extends Decorator {
    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doOtherThing();
    }

    private void doOtherThing() {
        System.out.println("功能B");
    }
}
