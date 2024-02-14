package bitcamp.myapp.handler.assignment;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.Prompt;
import bitcamp.util.TransactionManager;

import java.sql.SQLException;

public class AssignmentAddHandler extends AbstractMenuHandler {

  private AssignmentDao assignmentDao;
  private TransactionManager txManager;

  public AssignmentAddHandler(TransactionManager txManager, AssignmentDao assignmentDao) {
    this.txManager = txManager;
    this.assignmentDao = assignmentDao;
  }

  @Override
  protected void action(Prompt prompt) {
    try {
      Assignment assignment = new Assignment();
      assignment.setTitle(prompt.input("과제명? "));
      assignment.setContent(prompt.input("내용? "));
      assignment.setDeadline(prompt.inputDate("제출 마감일?(ex: 2023-12-25) "));

      txManager.begin();

      assignmentDao.add(assignment);
      assignmentDao.add(assignment);

      txManager.rollback();

    } catch (SQLException e) {
      prompt.println("과제 입력 중 오류 발생!");
      prompt.println("다시 입력해주세요");
    }
  }
}
