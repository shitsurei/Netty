package GoogleGRPC_08;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcServer {
    private Server server;

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();
        System.out.println("server started!");
        Runtime runtime = Runtime.getRuntime();
//        注册虚拟机关闭的钩子函数，使虚拟机在最终关闭之前执行一些操作（耗时较短）
        runtime.addShutdownHook(new Thread(() -> {
            System.out.println("关闭JVM");
            GrpcServer.this.stop();
        }, "t1"));
        System.out.println("test");
    }

    private void stop() {
        if (this.server != null)
            this.server.shutdown();
    }

    private void awaitTermination() throws InterruptedException {
        if (this.server != null)
//            带时间参数的await方法会在服务器启动一定时间后关闭
//            this.server.awaitTermination(50, TimeUnit.SECONDS);
            this.server.awaitTermination();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermination();
    }
}
