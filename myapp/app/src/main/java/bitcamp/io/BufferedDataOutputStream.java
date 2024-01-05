package bitcamp.io;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BufferedDataOutputStream extends DataOutputStream {

  private final byte[] buf = new byte[8192]; // 8kb
  int size;

  public BufferedDataOutputStream(String name) throws FileNotFoundException {
    super(name);
  }

  @Override
  public void write(int b) throws IOException {
    if (size == buf.length) {
      // 버퍼가 모두 찼다면, 버퍼에 저장된 데이터를 파일로 출력한다.
      flush();
    }
    buf[size++] = (byte) b;
  }

  @Override
  public void write(byte[] bytes) throws IOException {
    for (byte b : bytes) {
      if (size == buf.length) {
        flush();
      }
      buf[size++] = b;
    }
  }

  // 버퍼에 저장된 데이터를 파일로 출력한다.
  public void flush() throws IOException {
    super.write(buf, 0, size);
    size = 0;
  }

  // try-with-resources 구문에서 자동 호출되는 메서드이다.
  @Override
  public void close() throws IOException {
    // 출력 스트림을 닫기전에 버퍼에 남아있는 데이터를 파일로 출력한다.
    flush();
    super.close();
  }
}
