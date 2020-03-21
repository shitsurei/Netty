package GoogleProtobuf_06;

import GoogleProtobuf_06.Generate.DataInfo;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 02
 * 测试内容：
 * 1 构造好对象，生成字节数组
 * 2 从字节数组中读取对象并还原
 */
public class ProtobufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
//        A机器生成对象，将其序列化为字节数组
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张国荣").setAge(25).setAddress("西安").build();
//        转换为字节数组后可以在网络上进行传输
        byte[] studentByteArray = student.toByteArray();
//        B机器解析对象，将字节数组反序列化为原对象
        DataInfo.Student student2 = DataInfo.Student.parseFrom(studentByteArray);
        /**
         * name: "\345\274\240\345\233\275\350\215\243"
         * age: 25
         * address: "\350\245\277\345\256\211"
         */
        System.out.println(student2);
//        张国荣
        System.out.println(student2.getName());
//        25
        System.out.println(student2.getAge());
//        西安
        System.out.println(student2.getAddress());
    }
}
