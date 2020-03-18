package HelloWord;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 */
public class TestServer {
    public static void main(String[] args) {
//        1 定义两个事件循环组（两个都是死循环）
//        boss线程组只负责接受服务端的连接，不处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        worker线程组接受boss线程组分发的请求进行处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
//        2 定义和启动服务器，关联两个事件循环组和自定义的处理器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
//        3 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            4 关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
