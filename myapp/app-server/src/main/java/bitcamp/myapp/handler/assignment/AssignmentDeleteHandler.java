package bitcamp.myapp.handler.assignment;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.util.Prompt;

public class AssignmentDeleteHandler extends AbstractMenuHandler {

  private AssignmentDao assignmentDao;

  public AssignmentDeleteHandler(AssignmentDao assignmentDao) {
    this.assignmentDao = assignmentDao;
  }

  @Override
  protected void action(Prompt prompt) {
    int no = prompt.inputInt("번호? ");
    if (assignmentDao.delete(no) == 0) {
      prompt.println("과제 번호가 유효하지 않습니다.");
    } else {
      prompt.println("과제 삭제 성공");
    }
  }
}
