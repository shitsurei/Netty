package NIO_09.decorator;

/**
 * 装饰角色【核心】
 * 1 需要实现抽象构件角色
 * 2 需要持有一个抽象构件角色的引用
 */
public class Decorator implements Component {
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
