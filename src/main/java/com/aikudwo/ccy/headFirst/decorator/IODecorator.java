package com.aikudwo.ccy.headFirst.decorator;

import java.io.*;

/**
 * @author wls
 * @date 2019-07-03 09:22
 * 利用 IO 的装饰者模式，将读取到的字母小写
 */
public class IODecorator extends FilterInputStream {
    public IODecorator(InputStream in){
        super(in);
    }
    public int read() throws IOException{
            int c = super.read();
            return (c == -1 ? c : Character.toLowerCase(c));

    }
    public int read(byte[] b, int offset, int len) throws IOException {
        int result = super.read(b, offset, len);
        for (int i = offset; i < offset+result; i++) {
            b[i] = (byte)Character.toLowerCase((char)b[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int c;
        try {
            InputStream in =
                    new IODecorator(
                            new BufferedInputStream(
                                    new FileInputStream("C:/Users/Administrator/Desktop/test.txt")));
            while((c = in.read()) >= 0) {
                System.out.print((char)c);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
