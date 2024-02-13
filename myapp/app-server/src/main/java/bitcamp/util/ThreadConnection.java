package bitcamp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadConnection {

  private String jdbcUrl;
  private String username;
  private String password;

  // 개별 스레드용 db 커넥션 저장소
  private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

  public ThreadConnection(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection get() throws SQLException {
    // 현재 스레드에 보관중인 Connection 객체를 꺼낸다.
    Connection con = connectionThreadLocal.get();
    if (con == null) {
      // 없다면 새로 만든다.
      con = DriverManager.getConnection(jdbcUrl, username, password);
      // 나중에 또 사용할 수 있도록 현재 스레드에 보관
      connectionThreadLocal.set(con);
      System.out.printf("%s: db 커넥션 생성\n", Thread.currentThread().getName());
    } else {
      System.out.printf("%s: 기존에 보관했던 커넥션 사용\n", Thread.currentThread().getName());
    }
    return con;
  }

  public void remove() {
    // 현재 스레드에 보관중인 Connection 객체를 제거
    Connection con = connectionThreadLocal.get();
    if (con != null) {
      try {con.close();} catch (Exception e) {}
      connectionThreadLocal.remove();
      System.out.printf("%s: db 커넥션 제거\n", Thread.currentThread().getName());
    }
  }
}
