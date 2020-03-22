package ApacheThrift_07;

import ApacheThrift_07.generation.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {
    public static void main(String[] args) {
        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
            THsHaServer.Args args1 = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
            PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
            args1.protocolFactory(new TCompactProtocol.Factory());
            args1.processorFactory(new TProcessorFactory(processor));
            TServer server = new THsHaServer(args1);
            System.out.println("Thrift Server Start");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
