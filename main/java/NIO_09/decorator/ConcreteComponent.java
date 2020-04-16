package NIO_09.decorator;

/**
 * 具体构件角色
 */
public class ConcreteComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
