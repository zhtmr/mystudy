package bitcamp.myapp.handler.assignment;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.Prompt;
import java.util.ArrayList;

public class AssignmentAddHandler extends AbstractMenuHandler {

  private ArrayList<Assignment> objectRepository;


  public AssignmentAddHandler(ArrayList<Assignment> objectRepository, Prompt prompt) {
    super(prompt);
    this.objectRepository = objectRepository;
  }

  @Override
  protected void action() { // MenuHandler 인터페이스 대신 AbstractMenuHandler 클래스에 선언된 action() 추상 메서드 구현
    // --> super.action() 을 여기서 직접 호출할 필요가 없어진다.
    try {
      Assignment assignment = new Assignment();
      assignment.setTitle(this.prompt.input("과제명? "));
      assignment.setContent(this.prompt.input("내용? "));
      assignment.setDeadline(this.prompt.inputDate("제출 마감일?(ex: 2023-12-25) "));

      this.objectRepository.add(assignment);

    } catch (Exception e) {
      System.out.println("과제 입력 중 오류 발생!");
      System.out.println("다시 시도하시기 바랍니다.");
    }
  }
}
