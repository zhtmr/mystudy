package bitcamp.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

  Connection getConnection() throws SQLException;

  void returnConnection(Connection con);
}
