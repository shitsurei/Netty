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
 */
public class NIO3 {
    public static void main(String[] args) {
        fileRW();
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
}
