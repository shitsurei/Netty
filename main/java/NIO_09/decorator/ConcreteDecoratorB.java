package NIO_09.decorator;

public class ConcreteDecoratorB extends Decorator {
    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doOtherThing();
    }

    private void doOtherThing() {
        System.out.println("功能C");
    }
}
