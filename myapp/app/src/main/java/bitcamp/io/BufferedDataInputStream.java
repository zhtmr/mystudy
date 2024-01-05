package bitcamp.io;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BufferedDataInputStream extends DataInputStream {

  private final byte[] buf = new byte[8192];
  int size; // 읽은 바이트 수
  int cursor; // 버퍼 인덱스

  public BufferedDataInputStream(String name) throws FileNotFoundException {
    super(name);
  }

  @Override
  public int read() throws IOException {
    if (cursor == size) {
      cursor = 0;
      size = super.read(buf);  // 읽은 바이트 수를 리턴한다.
      if (size == -1) {
        return -1; // 읽을 데이터가 없다.
      }
    }
    return buf[cursor++] & 0xFF;  // sign-magnitude 방식으로 읽어야 한다. byte -> int 형변환시 맨앞자리가 1이면 음수로 바뀐다.
  }

  @Override
  public int read(byte[] arr) throws IOException {
    return read(arr, 0, arr.length);
  }

  @Override
  public int read(byte[] arr, int off, int len) throws IOException {
    for (int i = off, count = 0; count < len; count++, i++) {
      int b = read();
      if (b == -1) {
        return count > 0 ? count : -1;  // 읽은 데이터가 없으면 -1을 리턴해야한다.
      }
      arr[i] = (byte) b;
    }
    return len;
  }
}
