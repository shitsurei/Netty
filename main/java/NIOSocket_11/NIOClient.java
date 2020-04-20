package NIOSocket_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
//            客户端的关注事件为连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));
            while (true) {
                int number = selector.select();
                System.out.println("事件数量:" + number);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isConnectable()) {
                        SocketChannel client = (SocketChannel) key.channel();
//                        判断连接是否处于挂起状态
                        if (client.isConnectionPending()) {
                            client.finishConnect();
                            ByteBuffer sendBuffer = ByteBuffer.allocate(256);
                            sendBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            sendBuffer.flip();
                            client.write(sendBuffer);
//                            新建一个线程去读取控制台输入数据并发送至服务端
                            ExecutorService threadPool = Executors.newSingleThreadExecutor();
                            threadPool.submit(() -> {
                                while (true) {
                                    sendBuffer.clear();
                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                    String sendMessage = bufferedReader.readLine();
                                    sendBuffer.put(sendMessage.getBytes());
                                    sendBuffer.flip();
                                    client.write(sendBuffer);
                                }
                            });
                        }
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        iterator.remove();
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        int count = client.read(buffer);
                        if (count > 0) {
                            buffer.flip();
                            String receiveMessage = new String(buffer.array(), 0, count);
                            System.out.println("接收到服务端信息：" + receiveMessage);
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
