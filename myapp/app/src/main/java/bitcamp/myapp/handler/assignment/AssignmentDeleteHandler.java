package bitcamp.myapp.handler.assignment;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Assignment;
import java.util.ArrayList;

public class AssignmentDeleteHandler implements MenuHandler {

  Prompt prompt;
  ArrayList<Assignment> assignmentRepository;

  public AssignmentDeleteHandler(Prompt prompt, ArrayList<Assignment> assignmentRepository) {
    this.prompt = prompt;
    this.assignmentRepository = assignmentRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR, menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    if (assignmentRepository.remove(index) == null) {
      System.out.println("과제 번호가 유효하지 않습니다.");
    }

  }
}
