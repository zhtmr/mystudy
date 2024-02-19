package bitcamp.myapp.listener;

import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.mysql.AssignmentDaoImpl;
import bitcamp.myapp.dao.mysql.BoardDaoImpl;
import bitcamp.myapp.dao.mysql.MemberDaoImpl;
import bitcamp.util.DBConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
  // 웹 어플리케이션이 사용할 자원을 준비시키고 해제시키는 역할

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("웹 어플리케이션 자원 준비");
    // DB con, DAO, txManager
    DBConnectionPool connectionPool = new DBConnectionPool(
        //              "jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123"
        "jdbc:mysql://127.0.0.1/studydb", "study", "Bitcamp!@#123");
    AssignmentDao assignmentDao = new AssignmentDaoImpl(connectionPool);
    MemberDao memberDao = new MemberDaoImpl(connectionPool);
    BoardDao boardDao = new BoardDaoImpl(connectionPool);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("웹 어플리케이션 자원 해제");
  }
}
