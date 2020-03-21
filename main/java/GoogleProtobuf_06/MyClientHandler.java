package GoogleProtobuf_06;

import GoogleProtobuf_06.Generate.AnimalInfo;
import GoogleProtobuf_06.Generate.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class MyClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(2);
        AnimalInfo.Type animal = null;
        if (random == 0) {
            animal = AnimalInfo.Type.newBuilder().setAT(AnimalInfo.Type.AnimalType.DogType).setDog(
                    AnimalInfo.Dog.newBuilder().setName("旺旺").setAge(6)
            ).build();
        } else if (random == 1) {
            animal = AnimalInfo.Type.newBuilder().setAT(AnimalInfo.Type.AnimalType.CatType).setCat(
                    AnimalInfo.Cat.newBuilder().setColor("red").setAddress("上海")
            ).build();
        }
        ctx.writeAndFlush(animal);
//        ctx.writeAndFlush(DataInfo.Student.newBuilder().setName("张三").setAge(11).setAddress("北京"));
        ctx.close();
    }
}
