package ChatExample_03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChatClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost",8899).sync();
//            获取channel对象
            Channel channel = channelFuture.channel();
//            IO操作获取客户端在控制台输入的信息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true){
//                将客户端输入的信息发送到服务端
                channel.writeAndFlush(bufferedReader.readLine()+"\r\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
