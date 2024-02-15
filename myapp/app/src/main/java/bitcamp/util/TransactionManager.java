package bitcamp.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

  ConnectionPool connectionPool;

  public TransactionManager(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  public void begin() throws SQLException {
    connectionPool.getConnection().setAutoCommit(false);
    System.out.printf("[%s] 트랜잭션 시작\n", Thread.currentThread().getName());
  }

  public void commit() throws SQLException {
    connectionPool.getConnection().commit();
    complete();
  }

  public void rollback() throws SQLException {
    connectionPool.getConnection().rollback();
    System.out.printf("[%s] 트랜잭션 롤백!!\n", Thread.currentThread().getName());
    complete();
  }

  private void complete() throws SQLException {
    Connection con = connectionPool.getConnection();
    con.setAutoCommit(true);
    con.close();
    System.out.printf("[%s] 트랜잭션 종료\n", Thread.currentThread().getName());
  }

}
