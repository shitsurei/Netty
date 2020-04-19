package NIO_09;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 04
 * 读写文件
 * buffer相关的方法可以分为相对方法和绝对方法：
 * 1 相对方法：一般不含参数，其操作会自动影响buffer的position和limit位置
 * 2 绝对方法：通过传入参数来设定position和limit指向的位置
 *
 * buffer中不仅仅可以存放字节，还可以存放任何类型的数据，但是按顺序存取的类型必须保持一致，否则会出现异常
 * 【这种特性可用于读取自定义数据格式的文件】
 *
 * 分片buffer【slice方法】：用于生成一份原来buffer上某个区间一个buffer引用【新的buffer和原buffer共享相同的底层数组】
 *
 * 【只读buffer】通过buffer对象的asReadOnlyBuffer方法获取，可用于传递参数，保证buffer对象中的数据安全
 * 底层实现为使用原buffer的position，limit等参数创建一个HeapByteBufferR对象，该对象和原buffer对象独立，且写方法实现为直接抛异常
 */
public class NIO3 {
    public static void main(String[] args) {
        fileRW();
        diffType();
        testSlice();
    }

    public static void fileRW() {
        try {
            FileInputStream inputStream = new FileInputStream("src/main/java/NIO_09/NIO_test.txt");
            FileOutputStream outputStream = new FileOutputStream("src/main/java/NIO_09/NIO_test2.txt");
            FileChannel inputChannel = inputStream.getChannel();
            FileChannel outputChannel = outputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(256);
            while (true) {
                /**
                 * 如果注释掉clear方法，程序会不断读写文件
                 * 原因是下一次read时position和limit指向同一个位置，无字符可读
                 * 因此会返回0，跳过终止判断，继续执行flip方法，position重新回到buffer开头位置，再将缓冲区的数据写入文件一次
                 * read = 40
                 * read = 0
                 * read = 0
                 * read = 0
                 * read = 0
                 * read = 0
                 * read = 0
                 * ……
                 */
                buffer.clear();
//                read方法返回读取字节个数，如果无字节可读会返回-1
                int read = inputChannel.read(buffer);
                /**
                 * read = 40
                 * read = -1
                 */
                System.out.println("read = " + read);
                if (read == -1)
                    break;
                buffer.flip();
                outputChannel.write(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void diffType() {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(1000);
        buffer.putLong(999999999999999L);
        buffer.putChar('你');
        buffer.putShort((short) 33);
        buffer.putDouble(13.33);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getDouble());
        /**
         * 1000
         * 999999999999999
         * 你
         * 33
         * 13.33
         */
    }

    public static void testSlice() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
//        绝对操作设置position和limit位置
        buffer.position(2).limit(6);
        ByteBuffer slice = buffer.slice();
        for (int i = 0; i < slice.capacity(); i++) {
            slice.put((byte) (slice.get(i) * 2));
        }
//        绝对操作复原position和limit位置
        buffer.position(0).limit(buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
//            0 1 4 6 8 10 6 7 8 9 其中[2,6)区间的值因为共享引用所以对分片buffer的操作作用于原buffer上
            System.out.print(buffer.get() + " ");
        }
    }
}
