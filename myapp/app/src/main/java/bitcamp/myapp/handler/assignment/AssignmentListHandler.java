package bitcamp.myapp.handler.assignment;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.vo.Assignment;
import java.util.ArrayList;

public class AssignmentListHandler implements MenuHandler {

  ArrayList<Assignment> assignmentRepository;

  public AssignmentListHandler(ArrayList<Assignment> assignmentRepository) {
    this.assignmentRepository = assignmentRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR, menu.getTitle());
    System.out.printf("%-20s\t%s\n", "과제", "제출마감일");

    Assignment[] assignments = this.assignmentRepository.toArray(
        new Assignment[0]);

    for (Assignment assignment : assignments) {
      System.out.printf("%-20s\t%s\n", assignment.title, assignment.deadline);
    }
  }
}
