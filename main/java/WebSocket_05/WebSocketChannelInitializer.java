package WebSocket_05;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast(new ChunkedWriteHandler());
//        对HTTP消息进行聚合，将分段的信息聚合成一个完整的HTTP请求和响应
        channelPipeline.addLast(new HttpObjectAggregator(8192));
//        WebSocket握手和控制帧（Close，Ping，Pong）的处理，文本和二进制数据都会传递给下一个处理器处理
//        WebSocket协议路径：ws://server:port/context_path，例如ws://localhost:999/ws
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        channelPipeline.addLast(new TextWebSocketFrameHandler());
    }
}
