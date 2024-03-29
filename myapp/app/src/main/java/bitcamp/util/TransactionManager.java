package bitcamp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TransactionManager {
  private final Log log = LogFactory.getLog(this.getClass());
  ConnectionPool connectionPool;

  public TransactionManager(ConnectionPool connectionPool) {
    log.debug("TransactionManager() 호출됨");
    this.connectionPool = connectionPool;
  }

  public void begin() throws SQLException {
    connectionPool.getConnection().setAutoCommit(false);
    log.debug(String.format("[%s] 트랜잭션 시작\n", Thread.currentThread().getName()));
  }

  public void commit() throws SQLException {
    connectionPool.getConnection().commit();
    complete();
  }

  public void rollback() throws SQLException {
    connectionPool.getConnection().rollback();
    log.debug(String.format("[%s] 트랜잭션 롤백!!\n", Thread.currentThread().getName()));
    complete();
  }

  private void complete() throws SQLException {
    Connection con = connectionPool.getConnection();
    con.setAutoCommit(true);
    con.close();
    log.debug(String.format("[%s] 트랜잭션 종료\n", Thread.currentThread().getName()));
  }

}
