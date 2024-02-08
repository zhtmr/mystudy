package bitcamp.myapp.handler.assignment;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.Prompt;

public class AssignmentAddHandler extends AbstractMenuHandler {

  private AssignmentDao assignmentDao;


  public AssignmentAddHandler(AssignmentDao assignmentDao) {
    this.assignmentDao = assignmentDao;
  }

  @Override
  protected void action(Prompt prompt) { // MenuHandler 인터페이스 대신 AbstractMenuHandler 클래스에 선언된 action() 추상 메서드 구현
    // --> super.action() 을 여기서 직접 호출할 필요가 없어진다.
    Assignment assignment = new Assignment();
    assignment.setTitle(prompt.input("과제명? "));
    assignment.setContent(prompt.input("내용? "));
    assignment.setDeadline(prompt.inputDate("제출 마감일?(ex: 2023-12-25) "));

    this.assignmentDao.add(assignment);
  }
}
