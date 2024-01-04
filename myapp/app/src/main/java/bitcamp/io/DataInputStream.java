package bitcamp.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DataInputStream extends FileInputStream {
  public DataInputStream(String name) throws FileNotFoundException {
    super(name);
  }

  public short readShort() throws IOException {
    return (short) (read() << 8 | read());
  }

  public int readInt() throws IOException {
    return (read() << 24 | read() << 16 | read() << 8 | read());
  }

  public long readLong() throws IOException {
    return ((long) read() << 56 | (long) read() << 48 | (long) read() << 40 | (long) read() << 32 | (long) read() << 24 | (long) read() << 16 | (long) read() << 8 | (long) read());
  }

  public boolean readBoolean() throws IOException {
    return read() == 1; // 0이면 false, 1이면 true 리턴한다.
  }

  public String readUTF() throws IOException {
    int len = readShort();
    //    byte[] buf = new byte[len];
    //    read(buf, 0, len);
    byte[] buf = readNBytes(len); // 위와 같은 코드 (java11 부터 지원가능)
    return new String(buf, 0, len, StandardCharsets.UTF_8);
  }

}
