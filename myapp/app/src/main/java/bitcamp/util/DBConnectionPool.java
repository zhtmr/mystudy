package bitcamp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class DBConnectionPool implements ConnectionPool{
  private final Log log = LogFactory.getLog(this.getClass());

  @Value("${jdbc.url}")
  private String jdbcUrl;

  @Value("${jdbc.username}")
  private String username;

  @Value("${jdbc.password}")
  private String password;

  // DB 커넥션 목록
  ArrayList<Connection> connections = new ArrayList<>();

  // 개별 스레드용 db 커넥션 저장소
  private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

  public DBConnectionPool() {
    log.debug("DB con pool 호출됨");
  }

  @Override
  public Connection getConnection() throws SQLException {
    // 현재 스레드에 보관중인 Connection 객체를 꺼낸다.
    Connection con = connectionThreadLocal.get();
    // 스레드에 보관된 Connection 이 없다면,
    if (con == null) {
      if (!connections.isEmpty()) {
        // 스레드 풀에 놀고 있는 Connection 이 있다면 꺼낸다.
        con = connections.removeFirst();  // 목록에서 맨 처음 객체를 꺼낸다.
        log.debug(String.format("%s: db 커넥션 풀에서 꺼냄\n", Thread.currentThread().getName()));

      } else {
        // 스레드 풀에도 놀고 있는 Connection 이 없다면 새로 Connection 을 만든다.
        con = new ConnectionProxy(this, DriverManager.getConnection(jdbcUrl, username, password));
        log.debug(String.format("%s: db 커넥션 생성\n", Thread.currentThread().getName()));
      }

      // 현재 스레드에  Connection 보관한다.
      connectionThreadLocal.set(con);
    } else {   // 스레드에 보관된 Connection 이 있다면
      log.debug(String.format("%s: 스레드에 보관된 DB 커넥션 리턴\n", Thread.currentThread().getName()));
    }
    return con;
  }

  @Override
  public void returnConnection(Connection con) {
    // 스레드에 보관중인 Connection 객체를 제거
    connectionThreadLocal.remove();

    // Connection 을 커넥션 풀에 반환
    connections.add(con);

    log.debug(String.format("%s: db 커넥션을 pool 에 반환\n", Thread.currentThread().getName()));
  }

  public void closeAll() {
    for (Connection con : connections) {
      ((ConnectionProxy) con).realClose();
    }
  }
}
