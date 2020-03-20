package Socket_02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
/**
 * 可以同时开启多个客户端进程（例如pid为4456和452）同时访问8899端口的服务
 *   TCP    0.0.0.0:8899           0.0.0.0:0              LISTENING       8412
 *   TCP    127.0.0.1:6175         127.0.0.1:8899         ESTABLISHED     4456
 *   TCP    127.0.0.1:6197         127.0.0.1:8899         ESTABLISHED     452
 *   TCP    127.0.0.1:8899         127.0.0.1:6175         ESTABLISHED     8412
 *   TCP    127.0.0.1:8899         127.0.0.1:6197         ESTABLISHED     8412
 *   TCP    [::]:8899              [::]:0                 LISTENING       8412
 */
