package bitcamp.myapp.menu.assignment;

import bitcamp.myapp.handler.Menu;
import bitcamp.myapp.handler.MenuHandler;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.AnsiEscape;
import bitcamp.util.Prompt;
import java.util.ArrayList;

public class AssignmentAddHandler implements MenuHandler {

  Prompt prompt;
  ArrayList<Assignment> assignmentRepository;

  public AssignmentAddHandler(Prompt prompt, ArrayList<Assignment> assignmentRepository) {
    this.prompt = prompt;
    this.assignmentRepository = assignmentRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR, menu.getTitle());

    Assignment assignment = new Assignment();
    assignment.setTitle(this.prompt.input("과제명? "));
    assignment.setContent(this.prompt.input("내용? "));
    assignment.setDeadline(this.prompt.input("제출 마감일? "));

    assignmentRepository.add(assignment);
  }
}
