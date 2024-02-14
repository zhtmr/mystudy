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
  }

  public void commit() throws SQLException {
    connectionPool.getConnection().commit();
    complete();
  }

  public void rollback() throws SQLException {
    connectionPool.getConnection().rollback();
    complete();
  }

  private void complete() throws SQLException {
    Connection con = connectionPool.getConnection();
    con.setAutoCommit(true);
    con.close();
  }

}
