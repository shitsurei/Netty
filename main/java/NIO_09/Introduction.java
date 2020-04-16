package NIO_09;

/**
 * 01
 * NIO 非阻塞IO
 * IO内容总结回顾：
 * 流：Java程序通过流来完成输入输出，是生产和消费信息的抽象，是处理输入输出的一个清洁的方法
 * 分类：
 * 从功能上分为输入流和输出流，其中输入输出是以应用程序本身作为参照的；
 * 从流结构上分为字符流（只是对字节流做了一层包装）和字节流，其中字节流更为底层
 * 【字符流的顶层接口是Reader和Writer，字节流的顶层接口为InputStream和OutputStream】
 * 从流的读取方式可以分为节点流（从特定的地方读取的流，如磁盘或一块特定的内存区）和过滤流（依附于一个节点流，进行包装）
 * 【InputStream类之下除了FilterInputStream之外的子类都是节点流，FilterInputStream的子类都是过滤流，输出流结构类似】
 * 读/写数据的逻辑（一个流只能是输入流或者输出流）：
 * open a stream
 * while more information
 * read/write information
 * close the stream
 * 【IO流的装饰者模式/包装模式（wrapper）】实现流功能的动态获取和增强
 * 以对客户端透明的方式动态扩展对象的功能，是继承关系一个替代方案
 * 角色：
 * 1 抽象构建角色（Component）：给出一个抽象接口，以规范准备接收附加责任的对象【对应IO流中的InputStream】
 * 2 具体构建关系（Concrete Component）：定义一个将要接收附加责任的类【对应IO流中的节点流，如FileInputStream】
 * 3 装饰角色（Decorator）：持有一个构件对象的引用，并定义一个与构建接口一致的接口【对应过滤流的顶层角色FilterInputStream】
 * 4 具体装饰角色（Concrete Decorator）：负责给构件角色添加附加职责【对应具体的过滤流类，如Buffered】
 */
public class Introduction {
}
