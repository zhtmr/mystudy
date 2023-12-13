package bitcamp.myapp.menu.assignment;

import bitcamp.myapp.handler.Menu;
import bitcamp.myapp.handler.MenuHandler;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.Prompt;
import java.util.ArrayList;

public class AssignmentModifyHandler implements MenuHandler {

  Prompt prompt;
  ArrayList<Assignment> assignmentRepository;

  public AssignmentModifyHandler(Prompt prompt, ArrayList<Assignment> assignmentRepository) {
    this.prompt = prompt;
    this.assignmentRepository = assignmentRepository;
  }

  @Override
  public void action(Menu menu) {
    int index = this.prompt.inputInt("번호? ");
    Assignment old = this.assignmentRepository.get(index);
    if (old == null) {
      System.out.println("과제 번호가 유효하지 않습니다.");
      return;
    }
    Assignment assignment = new Assignment();
    assignment.title = this.prompt.input("과제명(%s)? ", assignment.title);
    assignment.content = this.prompt.input("내용(%s)? ", assignment.content);
    assignment.deadline = this.prompt.input("제출 마감일(%s)? ", assignment.deadline);

    this.assignmentRepository.set(index, assignment);
  }
}
