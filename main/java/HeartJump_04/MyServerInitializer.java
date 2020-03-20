package HeartJump_04;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
//        空闲检测处理器：在一定时间内没有读和写操作会触发IdleState事件
        channelPipeline.addLast(new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS));
        channelPipeline.addLast(new MyServerHandler());
    }
}
