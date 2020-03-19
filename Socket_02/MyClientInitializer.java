package Socket_02;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("Client LengthFieldBasedFrameDecoder",new LengthFieldBasedFrameDecoder(
                Integer.MAX_VALUE,0,4,0,4));
        channelPipeline.addLast("Client LengthFieldPrepender",new LengthFieldPrepender(4));
        channelPipeline.addLast("Client StringDecoder",new StringDecoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("Client StringEncoder",new StringEncoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("MyClientHandler",new MyClientHandler());
    }
}
