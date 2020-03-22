package ApacheThrift_07;

import ApacheThrift_07.generation.Person;
import ApacheThrift_07.generation.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {
    public static void main(String[] args) {
//        客户端和服务器端的传输层和协议相同时才能识别
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);
        try {
            transport.open();
            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getAge());
            System.out.println(person.isMarried());
            System.out.println("----------------");
            Person person2 = new Person().setName("李四").setAge(30).setMarried(true);
            client.savePerson(person2);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            transport.close();
        }
    }
}
