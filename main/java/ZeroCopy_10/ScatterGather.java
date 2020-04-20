package ZeroCopy_10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 02
 * 关于buffer的Scattering和Gathering
 * 用于将来自于一个channel的数据按顺序读到多个buffer中
 * 【作用：自定义协议中将多个消息头消息体分别放入不同的buffer，避免读入一个buffer后再进行解析】
 */
public class ScatterGather {
    public static void main(String[] args) {
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(8899);
            channel.socket().bind(address);

            int messageLen = 2 + 3 + 4;
//            我们可以把这三个buffer理解为请求中消息头和消息体的不同部分，其大小规定好的
//            我们在读取时只需要按字节个数读取到不同buffer中即可操作
            ByteBuffer[] buffers = new ByteBuffer[3];
            buffers[0] = ByteBuffer.allocate(2);
            buffers[1] = ByteBuffer.allocate(3);
            buffers[2] = ByteBuffer.allocate(4);
            /**
             * 通过NetCat工具连接本地8899端口，命令是 nc localhost 8899
             * 输入9个字符（8个显式字符加一个回车\r字符）后服务器写入的9个字符被返回回来
             * zhangguo（输入）
             * acaceaceg（输出）
             * 控制台显示：
             * bytesRead: 9
             * position:2,limit:2
             * position:3,limit:3
             * position:4,limit:4
             * bytesRead:9,byteWritten:9,messageLen:9
             */
            SocketChannel socketChannel = channel.accept();
//            不断循环监听端口请求
            while (true) {
                int bytesRead = 0;
                while (bytesRead < messageLen) {
//                    【读取的过程中会优先读满靠前的buffer，所有buffer未读满之前不会执行返回数据的操作】
                    long r = socketChannel.read(buffers);
                    bytesRead += r;
//                    bytesRead: 9
                    System.out.println("bytesRead: " + bytesRead);
//                    三个buffer都读满了
//                    position:2,limit:2
//                    position:3,limit:3
//                    position:4,limit:4
                    Arrays.asList(buffers).stream()
                            .map(buffer -> "position:" + buffer.position() + ",limit:" + buffer.limit()).
                            forEach(System.out::println);
                }
//                反转buffer读写状态，回送信息
                Arrays.asList(buffers).forEach(ByteBuffer::flip);
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < buffers[i].capacity(); j++)
//                        写入数据
                        buffers[i].put(j, (byte) ('a' + 2 * j));
                long byteWritten = 0;
                while (byteWritten < messageLen) {
                    long w = socketChannel.write(buffers);
                    byteWritten += w;
                }
                Arrays.asList(buffers).forEach(ByteBuffer::clear);
//                bytesRead:9,byteWritten:9,messageLen:9
                System.out.println("bytesRead:" + bytesRead + ",byteWritten:" + byteWritten + ",messageLen:" + messageLen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
