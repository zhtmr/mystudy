package bitcamp.myapp.handler.assignment;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.Prompt;
import bitcamp.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class AssignmentAddHandler extends AbstractMenuHandler {

  private AssignmentDao assignmentDao;
  private DBConnectionPool connectionPool;

  public AssignmentAddHandler(DBConnectionPool connectionPool, AssignmentDao assignmentDao) {
    this.connectionPool = connectionPool;
    this.assignmentDao = assignmentDao;
  }

  @Override
  protected void action(Prompt prompt) {
    Connection con = null;
    try {
      Assignment assignment = new Assignment();
      assignment.setTitle(prompt.input("과제명? "));
      assignment.setContent(prompt.input("내용? "));
      assignment.setDeadline(prompt.inputDate("제출 마감일?(ex: 2023-12-25) "));

      con = connectionPool.getConnection();
      con.setAutoCommit(false);

      assignmentDao.add(assignment);
      assignmentDao.add(assignment);

      con.rollback();
    } catch (SQLException e) {
      prompt.println("과제 입력 중 오류 발생!");
      prompt.println("다시 입력해주세요");
    } finally {
      // Connection 은 다른 작업할 때 다시 사용해야 하기 때문에 원래 상태로 되돌린다.
      try {con.setAutoCommit(true);} catch (Exception e) {}
      connectionPool.returnConnection(con);
    }
  }
}
