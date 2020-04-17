package NIO_09;

import java.nio.IntBuffer;

/**
 * 03
 * NIO Buffer中三个重要状态属性的含义：
 * 1 capacity   buffer的最大容量,非负且不会变化，每一次flip之后都重新指向buffer头位置
 * 2 position   buffer下一个要去去读或写的元素的索引，非负且不能超过limit位置
 * 3 limit      buffer无法去读或写的第一个元素的索引，初始时和capacity指向同一个位置（buffer数组末尾的下一个位置）
 * 【调用flip时position的位置会从当前位置改为buffer的头位置，此时需要limit来记录之前position的位置，防止下一次的读或写操作越界】
 * 关于buffer的操作可以分为相对操作（从当前的position位置开始读写）和绝对操作（接受一个显式的元素索引，不会影响position）
 * mark和resetting 用于读取过程标记位置和重新回到之前标记的位置，mark可以不定义，定义时需要非负且不能超过position
 * 0 <= mark <= position <= limit <= capacity
 */
public class NIO2 {
    public static void main(String[] args) {
        flipTest();
    }

//    buffer子类中的方法调用支持责任链式调用，因为其方法返回的都是修改相关属性后的buffer对象
    public static void methodIntroduction() {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        /**
         * clear方法会将buffer重置为初始状态，等待读或写操作，即
         * 1 position置为0
         * 2 limit置为capacity
         */
        intBuffer.clear();
        /**
         * flip方法会改变buffer当前的读写状态，并
         * 1 将limit设置为当前的position
         * 2 将position置为0
         * 3 mark置为-1
         */
        intBuffer.flip();
        /**
         * rewind方法会将当前的读写位置清零，即重头开始读写
         * 1 position置为0
         * 2 limit保持不变
         * 3 mark置为-1
         */
        intBuffer.rewind();
    }

    public static void flipTest() {
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("capacity: " + buffer.capacity());//capacity: 10
        System.out.println("position: " + buffer.position());//position: 0
        for (int i = 0; i < 5; i++)
            buffer.put(i);
        System.out.println("before flip limit: " + buffer.limit());//before flip limit: 10
        buffer.flip();
        System.out.println("after flip limit: " + buffer.limit());//after flip limit: 5
        /**
         * enter loop
         * capacity: 10
         * position: 0
         * limit: 5
         * 0
         * capacity: 10
         * position: 1
         * limit: 5
         * 1
         * capacity: 10
         * position: 2
         * limit: 5
         * 2
         * capacity: 10
         * position: 3
         * limit: 5
         * 3
         * capacity: 10
         * position: 4
         * limit: 5
         * 4
         */
        System.out.println("enter loop");
        while (buffer.hasRemaining()) {
            System.out.println("capacity: " + buffer.capacity());
            System.out.println("position: " + buffer.position());
            System.out.println("limit: " + buffer.limit());
            System.out.println(buffer.get());
        }
    }
}
