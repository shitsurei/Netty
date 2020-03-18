package HelloWord;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
//    回调方法，可以在其中添加若干个自定义的处理器
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        流式处理，每个请求会按顺序进行处理
        ChannelPipeline pipeline = socketChannel.pipeline();
//        对web请求进行编码解码（Netty本身内置的处理器）
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
//        自定义处理器
        pipeline.addLast("TestHttpServerHandler", new TestHttpServerHandler());
    }
}
