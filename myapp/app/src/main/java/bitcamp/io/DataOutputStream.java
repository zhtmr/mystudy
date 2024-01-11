//package bitcamp.io;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//public class DataOutputStream extends FileOutputStream {
//  public DataOutputStream(String name) throws FileNotFoundException {
//    super(name);
//  }
//
//  // 1 byte 출력하기
//  public void writeShort(int value) throws IOException {
//    write(value >> 8);
//    write(value);
//  }
//
//  // 4 byte 출력하기
//  public void writeInt(int value) throws IOException {
//    write(value >> 24);
//    write(value >> 16);
//    write(value >> 8);
//    write(value);
//  }
//
//  // 8 byte 출력하기
//  public void writeLong(long value) throws IOException {
//    write((int) (value >> 56));
//    write((int) (value >> 48));
//    write((int) (value >> 40));
//    write((int) (value >> 32));
//    write((int) (value >> 24));
//    write((int) (value >> 16));
//    write((int) (value >> 8));
//    write((int) value);
//  }
//
//  // 문자열 출력
//  public void writeUTF(String value) throws IOException {
//    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
//    writeShort(bytes.length);
//    write(bytes);
//  }
//
//  public void writeBoolean(boolean value) throws IOException {
//    if (value) {
//      write(1);
//    } else {
//      write(0);  // 실제로는 0x00 또는 0x01 이다. 즉, 논리값 출력.
//    }
//  }
//
//}
