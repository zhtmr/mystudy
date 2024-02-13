package bitcamp.myapp.handler.assignment;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.DBConnectionPool;
import bitcamp.util.Prompt;

import java.sql.Connection;

public class AssignmentModifyHandler extends AbstractMenuHandler {

  private AssignmentDao assignmentDao;
  private DBConnectionPool connectionPool;

  public AssignmentModifyHandler(DBConnectionPool connectionPool, AssignmentDao assignmentDao) {
    this.connectionPool = connectionPool;
    this.assignmentDao = assignmentDao;
  }


  @Override
  protected void action(Prompt prompt) {
    Connection con = null;
    try {
      con = connectionPool.getConnection();
      int no = prompt.inputInt("번호? ");
      Assignment old = this.assignmentDao.findBy(no);
      if (old == null) {
        prompt.println("과제 번호가 유효하지 않습니다.");
        return;
      }

      Assignment assignment = new Assignment();
      assignment.setNo(old.getNo());
      assignment.setTitle(prompt.input("과제명(%s)? ", old.getTitle()));
      assignment.setContent(prompt.input("내용(%s)? ", old.getContent()));
      assignment.setDeadline(prompt.inputDate("제출 마감일(%s)? ", old.getDeadline()));
      this.assignmentDao.update(assignment);
    } catch (Exception e) {
      prompt.println("수정 오류!");
    } finally {
      connectionPool.returnConnection(con);
    }
  }
}
