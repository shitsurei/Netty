package ZeroCopy_10;

import java.nio.ByteBuffer;

/**
 * 01
 * 直接缓冲和零拷贝
 * 操作系统在执行IO操作的过程中，需要先把位于Java堆内存上的数据拷贝到堆外内存，再通过堆外内存和IO设备进行直接交互
 * 【操作系统不直接访问Java堆内存的原因并非没有权限，系统在内核态下可以访问内存中的任何地址，
 *      而是因为Java堆空间会不定时发生GC，复制算法和标记整理算法都会导致堆空间对象的移动】
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
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
    }
}
