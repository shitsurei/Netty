package ZeroCopy_10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 03
 * Selector对象是通过SPI服务来加载创建的（类似JDBC的实现），主要用于网络编程，可以通过一个线程监听多个端口，处理多个客户端的请求事件
 * 传统的Socket编程流程：
 * 【服务器端】
 * 1 构造一个ServerSocket
 * 2 调用bind方法将socket对象绑定到某个端口上
 * 【以下3和4两个步骤是放入死循环中，不断监听客户端请求的】
 * 3 调用socket对象的accept方法（阻塞方法），阻塞等待客户端的连接，直到接收到客户端请求才返回
 * 4 创建一个新的线程传入接收到的客户端请求（或者输入输出流），在线程的run方法中执行其他业务逻辑
 * （在新的线程中处理相应的业务逻辑不会影响主线程处理新的客户端请求，可以提高吞吐量）
 * 【客户端】
 * 1 构造一个Socket对象，传入要连接的IP地址和端口号
 * 2 调用socket对象的connect方法
 * 3 通过输出流发送数据
 * 【传统的Socket编程存在一个弊端，如果并发请求较大，会创建过多的线程资源，影响性能，需要线程池技术来处理】
 * <p>
 * 异步编程模型的核心概念：事件
 * Selector（JDK4引入）：一个多路传输可选择的channel对象
 * 通过SelectionKey标示和请求相关的事件，将通道注册到selector组件上
 * 一个selector对象会维护三种SelectionKey的集合：
 * 1 key set    通过keys()方法返回所有的事件可能性
 * 2 selected-key set   通过selectedKeys()方法返回感兴趣的事件
 * 3 cancelled-key set  保存取消的事件集合，取消一个key会导致通道在下一次选择的时候被删除
 * selector对象创建时这三个集合都是空的
 * <p>
 * 当注册的事件发生后
 */
public class SelectorSocket {
    public static void main(String[] args) {
        int[] port = new int[]{5000, 5001, 5002, 5003, 5004};
        try {
            Selector selector = Selector.open();
//            端口注册，使一个selector对象可以监听多个端口
            for (int i = 0; i < port.length; i++) {
//                针对面向流的channel
                ServerSocketChannel channel = ServerSocketChannel.open();
//                调整管道的阻塞模式，设置为非阻塞的管道
                channel.configureBlocking(false);
//                获取一个与该通道关联的socket对象
                ServerSocket socket = channel.socket();
                InetSocketAddress address = new InetSocketAddress(port[i]);
//                【绑定】将端口绑定到socket对象上
                socket.bind(address);
//                【注册】通过channel的register方法会导致某个key（第二个参数）添加到selector的key set集合当中
                channel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("监听端口:" + port[i]);
            }
            while (true) {
//                select方法是一个阻塞的方法，一旦selector监听的几个端口有事件发生就会返回，返回值为事件的数量
                int numbers = selector.select();
                System.out.println("numbers: " + numbers);
//                selectedKeys方法会返回每个通道发生的事件组成的集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("selectionKeys：" + selectionKeys);
//                获取事件集合的迭代器
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
//                    是否有客户端连接（关注ACCEPT事件）
                    if (key.isAcceptable()) {
//                        通过key可以获取与selector关联的channel对象
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = channel.accept();
                        socketChannel.configureBlocking(false);
//                        真正连接对象的注册，表示下一次关注读事件
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("获得客户端连接:" + socketChannel);
//                        将处理完的事件从key set中移除
                        iterator.remove();
//                    是否有可读的数据
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        int byteRead = 0;
                        ByteBuffer buffer = ByteBuffer.allocateDirect(128);
                        while (true) {
                            buffer.clear();
                            int read = channel.read(buffer);
                            if (read <= 0)
                                break;
                            buffer.flip();
                            channel.write(buffer);
                            byteRead += read;
                        }
                        System.out.println("读取：" + byteRead + "字节，来自" + channel);
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
