package ApacheThrift_07;

import ApacheThrift_07.generation.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;


/**
 *
 */
public class ThriftServer {
    public static void main(String[] args) {
        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
            THsHaServer.Args args1 = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
            PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
            /**
             * 应用层协议（高层）
             * TBinaryProtocol（二进制格式）
             * TCompactProtocol（压缩格式）
             * TJSONProtocol（JSON格式）
             * TDebugProtocol（可读文本格式）
             */
            args1.protocolFactory(new TCompactProtocol.Factory());
            /**
             * 传输层协议（底层以什么形式从一端传给另一端）
             * TSocket（阻塞式socket，相当于Java中的ServerSocket）
             * TFramedTransport（以frame为单位进行传输，在非阻塞服务中使用）
             * TFileTransport（以文件形式进行传输）
             * TMemoryTransport（将内存用于IO，实现时使用了ByteArrayOutputStream）
             * TZlibTransport（使用zlib进行压缩，与其他传输方式联合使用，Java中无实现）
             */
            args1.transportFactory(new TFramedTransport.Factory());

            args1.processorFactory(new TProcessorFactory(processor));
            /**
             * 支持的服务模式：
             * TServer抽象类下有5个实现类
             * TSimpleServer（简单的单线程服务模型，常用于测试）
             * TThreadPoolServer（多线程服务模型，使用标准的阻塞式IO）
             * TNonblockingServer（多线程服务模型，使用非阻塞式IO，依赖TFramedTransport传输协议）
             * THsHaServer（半同步Half-sync半异步Half-async服务器，依赖TFramedTransport传输协议，其模型把读写任务引入线程池来处理，处理IO事件时异步，handler对rpc处理时同步）
             */
            TServer server = new THsHaServer(args1);
            System.out.println("Thrift Server Start");
//            服务器端启动（死循环）
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
