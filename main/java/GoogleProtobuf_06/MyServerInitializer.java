package GoogleProtobuf_06;

import GoogleProtobuf_06.Generate.AnimalInfo;
import GoogleProtobuf_06.Generate.DataInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
//        Protobuf解码器，参数是待解码对象的实例（写死的，只能处理Student类型）
//        pipeline.addLast(new ProtobufDecoder(DataInfo.Student.getDefaultInstance()));
//        如果一个Protobuf文件中存在多个对象，可以通过枚举的方式灵活处理
        pipeline.addLast(new ProtobufDecoder(AnimalInfo.Type.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new MyServerHandler());
    }
}
