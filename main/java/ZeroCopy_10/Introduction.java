package ZeroCopy_10;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 01
 * 直接缓冲和零拷贝
 * 操作系统在执行IO操作的过程中，需要先把位于Java堆内存上的数据拷贝到堆外内存，再通过堆外内存和IO设备进行直接交互
 * 【操作系统不直接访问Java堆内存的原因并非没有权限，系统在内核态下可以访问内存中的任何地址，
 * 而是因为Java堆空间会不定时发生GC，复制算法和标记整理算法都会导致堆空间对象的移动】
 * 【内存空间的复制速度要快于IO设备与内存空间的交互速度，因此将数据移动到对外空间的性价比比较高】
 * 零拷贝的好处在于直接在堆外内存进行数据的存储和操作，在与IO设备进行交互时减少一次拷贝操作
 * DirectByteBuffer对象虽然是Java堆上的对象（new出来的），但是其中保存的堆外空间的引用address
 * 在所有buffer对象的父类Buffer中保存着address变量，就是直接缓冲buffer用来保存堆外内存的地址
 * // Used only by direct buffers
 * // NOTE: hoisted here for speed in JNI GetDirectBufferAddress（为了JNI更快获取直接缓冲地址而将该变量升级至父类中）
 * long address;
 */
public class Introduction {
    public static void main(String[] args) {
        /**
         * allocateDirect方法会创建一个DirectByteBuffer对象返回，该对象所有的buffer操作都是对堆外内存直接进行操作
         * 由于堆外内存的操作主要是由JNI相关接口实现的，因此DirectByteBuffer类的底层实现中有许多VM类和Unsafe类的方法
         */
//        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        mapByteBuffer();
    }

    public static void mapByteBuffer() {
        /**
         * DirectByteBuffer的父类MappedByteBuffer用于读取一块文件的内存映射区域
         * 可以通过FileChannel的map方法获取
         */
        try {
//            获取随机访问文件，rw表示读写权限
            RandomAccessFile file = new RandomAccessFile("src/main/java/ZeroCopy_10/MappedByteBuffer_test.txt", "rw");
            FileChannel channel = file.getChannel();
//            获取文件的内存映射区域（读写模式，从0位置开始10个字节大小）
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
//            设置文件的锁对象（起始位置，字节数，是否为共享锁）
            FileLock lock = channel.lock(0, 10, false);
            byteBuffer.put(0, (byte) 'a');
            byteBuffer.put(7, (byte) 'b');
            lock.release();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
