package NIOSocket_11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class NIOServer {
    //    通过hash表维护所有客户端的连接信息
    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) {
        try {
//            NIO服务端模板代码
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(8899));
//            创建selector
            Selector selector = Selector.open();
//            将serverSocketChannel注册到selector上【serverSocketChannel关注连接事件】
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//            事件处理（网络程序的主线程一般都是死循环）
            while (true) {
                int number = selector.select();
                System.out.println("事件数量：" + number);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
//                    判断当前发生的事件是否是连接事件
                    if (key.isAcceptable()) {
//                        此处能够对象下转型成功的原因是
//                        【该事件的发生一定是因为serverSocketChannel注册到selector上导致连接事件进入了selector的key set】
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
//                        将SocketChannel注册到selector上【socketChannel关注数据读取事件】
                        client.register(selector, SelectionKey.OP_READ);
//                        将客户端连接进行维护
                        clientMap.put(UUID.randomUUID().toString(), client);
                        iterator.remove();
                    } else if (key.isReadable()) {
//                        发生读取数据事件一定是将client注册到selector导致的
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        int count = client.read(buffer);
                        if (count > 0) {
                            buffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            String receiveMessage = String.valueOf(charset.decode(buffer).array());
                            System.out.println("接收到客户端【" + client + "】的信息：" + receiveMessage);
                            String sendKey = null;
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (entry.getValue() == client) {
                                    sendKey = entry.getKey();
                                    break;
                                }
                            }
//                            将当前发送到服务器的信息转发给其他客户端
                            for (String otherKey : clientMap.keySet()) {
                                if (!otherKey.equals(sendKey)) {
                                    SocketChannel otherClient = clientMap.get(otherKey);
                                    ByteBuffer sendBuffer = ByteBuffer.allocate(256);
                                    sendBuffer.put((sendKey + "发送消息：" + receiveMessage).getBytes());
                                    sendBuffer.flip();
                                    otherClient.write(sendBuffer);
                                }
                            }
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
