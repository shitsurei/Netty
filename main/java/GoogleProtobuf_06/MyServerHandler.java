package GoogleProtobuf_06;

import GoogleProtobuf_06.Generate.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {
        System.out.println(msg.getName() + "," + msg.getAge() + "," + msg.getAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        连接中的异常处理
        cause.printStackTrace();
        ctx.close();
    }
}
