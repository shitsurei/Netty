package HeartJump_04;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
//    事件触发时的处理逻辑，默认会转发给管道中的下一个处理器
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String eventType = null;
            switch (((IdleStateEvent) evt).state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
//                写空闲
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
//                读写空闲
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);
            ctx.channel().close();
        }
    }
}
