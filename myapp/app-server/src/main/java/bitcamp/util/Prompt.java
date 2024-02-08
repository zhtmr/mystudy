package bitcamp.util;

import java.io.*;
import java.sql.Date;
import java.util.Stack;

public class Prompt implements AutoCloseable{

  Stack<String> breadcrumb = new Stack<>();

  private DataInputStream in;
  private DataOutputStream out;
  private StringWriter stringWriter = new StringWriter();
  private PrintWriter writer = new PrintWriter(stringWriter);


  public Prompt(DataInputStream in, DataOutputStream out) {
    this.in = in;
    this.out = out;
  }

  public String input(String str, Object... args)  {
    try {
      printf(str, args);
      end();
      return in.readUTF();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public int inputInt(String str, Object... args)  {
    return Integer.parseInt(this.input(str,args));
  }

  public float inputFloat(String str, Object... args)  {
    return Float.parseFloat(this.input(str, args));
  }

  public boolean inputBoolean(String str, Object... args) {
    return Boolean.parseBoolean(this.input(str, args));
  }

  public Date inputDate(String str, Object... args) {
    return Date.valueOf(this.input(str, args));
  }


  public void close() throws IOException {
    writer.close();
    stringWriter.close();
  }

  public void println(String str) {
    writer.println(str);
  }

  public void print(String str) {
    writer.print(str);
  }

  public void printf(String str, Object... args) {
    writer.printf(str, args);
  }

  public void end() throws Exception {
    // PrintWriter를 통해 출력한 내용은 StringWriter에 쌓여있다.
    // StringWriter에 쌓여있는 문자열을 꺼낸다.
    StringBuffer buffer = stringWriter.getBuffer();
    String content = buffer.toString();
    buffer.setLength(0);

    // 클라이언트로 전송. 서버의 응답이 완료된다
    out.writeUTF(content);
  }

  public void pushPath(String menu) {
    breadcrumb.push(menu);
  }

  public String popPath() {
    return breadcrumb.pop();
  }

  public String getFullPath() {
    return String.join("/", breadcrumb.toArray(new String[0]));
  }

}
