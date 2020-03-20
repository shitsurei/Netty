package HelloWord_01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
// inbound  对进来的请求的处理   outbound    对返回的响应的处理
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
//    读取客户端请求，向客户端返回响应
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            System.out.println("请求方法名：" + ((HttpRequest) httpObject).method().name());
            URI uri = new URI(((HttpRequest) httpObject).uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求favicon.ico");
                return;
            }

            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1
                    , HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            channelHandlerContext.writeAndFlush(response);
        }
    }

    /**
     * curl请求输出：
     * handlerAdded
     * channelRegistered
     * channelActive
     * 请求方法名：GET
     * channelInactive
     * channelUnregistered
     *
     * 浏览器请求输出：
     * handlerAdded
     * channelRegistered
     * handlerAdded
     * channelActive
     * channelRegistered
     * channelActive
     * 请求方法名：GET
     * 请求方法名：GET
     * 请求favicon.ico
     *
     * 浏览器第二次请求输出：
     * channelInactive
     * channelUnregistered
     * handlerAdded
     * channelRegistered
     * channelActive
     * 请求方法名：GET
     * channelInactive
     * channelUnregistered
     * 请求方法名：GET
     * 请求favicon.ico
     *
     * 关闭浏览器会话输出（保持连接）：
     * channelInactive
     * channelUnregistered
     *
     * HTTP协议是一个基于请求和响应的无状态的协议，底层是基于TCP的带连接的协议（socket）
     * HTTP1.0  短连接，收到请求后服务端就会关闭
     * HTTP1.1  长连接（keep-alive），服务器端到一定时间会主动关闭掉
     */

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }
}
