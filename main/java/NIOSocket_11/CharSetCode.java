package NIOSocket_11;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 02
 * 字符集编码
 * utf大端小端
 */
public class CharSetCode {
    public static void main(String[] args) {
        String inputFile = "src/main/java/NIOSocket_11/Charset_input.txt";
        String outputFile = "src/main/java/NIOSocket_11/Charset_output.txt";
        try {
            RandomAccessFile input = new RandomAccessFile(inputFile, "r");
            RandomAccessFile output = new RandomAccessFile(outputFile, "rw");
            long len = input.length();
            FileChannel inputChannel = input.getChannel();
            FileChannel outputChannel = output.getChannel();
//            将读取的文件映射到直接内存中
            MappedByteBuffer buffer = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, len);
//            设置字节数组和字符数组转换的编解码，统一用utf-8进行编解码
            Charset charset = Charset.forName("utf-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharsetEncoder encoder = charset.newEncoder();
            CharBuffer charBuffer = decoder.decode(buffer);
            ByteBuffer byteBuffer = encoder.encode(charBuffer);
//            写文件
            outputChannel.write(byteBuffer);
            inputChannel.close();
            outputChannel.close();
            input.close();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
