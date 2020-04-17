package NIO_09;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

/**
 * 02
 * JDK4开始添加
 * IO中最为核心的概念就是流，流是信息的载体，IO编程是面向流的编程，一个流只能是输入流或输出流，不可能同时继承两者
 * NIO中有三个核心概念：
 * 1 Selector   选择器
 * 2 Channel    通道【所有数据都是通过buffer进行的，不存在直接向channel读数据或写数据的情况】
 * 3 Buffer     缓冲区【指的是可以向其写入数据或从中读取数据的对象，类似IO中的流，并且支持即读又写，但是读写状态转换要调用flip方法】
 * NIO中是面向块（block）或缓冲区（buffer）编程的，buffer本身就是一块内存（通过一个数组实现），数据的读写都是通过buffer实现的
 * Java中的七种原生数据类型（除了布尔类型）都有对应的buffer类型
 */
public class NIO {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    //    写文件
    public static void test3() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/NIO_09/NIO_test.txt", true);
            FileChannel channel = fileOutputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(128);
            byte[] word = "\nthis is a byte array".getBytes();
            buffer.put(word);
            buffer.flip();
            channel.write(buffer);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    读文件
    public static void test2() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/NIO_09/NIO_test.txt");
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
            channel.read(byteBuffer);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                /**
                 * Char: h
                 * Char: e
                 * Char: l
                 * Char: l
                 * Char: o
                 * Char:
                 * Char: w
                 * Char: o
                 * Char: r
                 * Char: l
                 * Char: d
                 * Char:
                 * Char: w
                 * Char: e
                 * Char: l
                 * Char: c
                 * Char: o
                 * Char: m
                 * Char: e
                 */
                System.out.println("Char: " + (char) b);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    基本类型
    public static void test1() {
//        开辟一块大小为10的int类型缓冲区
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            int random = new SecureRandom().nextInt(20);
//            将数据读入缓冲区buffer
            buffer.put(random);
        }
//        实现缓冲区的读写切换
        buffer.flip();
        while (buffer.hasRemaining())
//            19 13 8 17 14 3 15 14 14 19
            System.out.print(buffer.get() + " ");
    }
}
