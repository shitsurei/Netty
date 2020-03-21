package GoogleProtobuf_06;

import GoogleProtobuf_06.Generate.AnimalInfo;
import GoogleProtobuf_06.Generate.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

//public class MyServerHandler extends SimpleChannelInboundHandler<DataInfo.Student> {
public class MyServerHandler extends SimpleChannelInboundHandler<AnimalInfo.Type> {

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {
//        System.out.println(msg.getName() + "," + msg.getAge() + "," + msg.getAddress());
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AnimalInfo.Type msg) throws Exception {
        AnimalInfo.Type.AnimalType aT = msg.getAT();
        if (aT == AnimalInfo.Type.AnimalType.DogType) {
            System.out.println(msg.getDog().getName() + "," + msg.getDog().getAge());
        } else if (aT == AnimalInfo.Type.AnimalType.CatType) {
            System.out.println(msg.getCat().getColor() + "," + msg.getCat().getAddress());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        连接中的异常处理
        cause.printStackTrace();
        ctx.close();
    }
}
